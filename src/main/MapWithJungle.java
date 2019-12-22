package main;

import java.util.*;

public class MapWithJungle implements IPositionChangeObserver{

	HashMap<Vector2d, TreeSet<Animal>> animalsOnPosition;
	private HashMap<Vector2d, Plant> plants;
	private Vector2d leftBottomCorner;
	private Vector2d rightUpperCorner;
	private Vector2d leftBottomCornerOfJungle;
	private Vector2d rightUpperCornerOfJungle;
	private int jungleArea;
	private int area;
	
	
	
	public  MapWithJungle(int width, int height, float jungleRatio, int howManyAnimalsOnStart, int howManyPlantsOnStart) throws Exception
	{
		this.leftBottomCorner=new Vector2d(1, 1);
		this.rightUpperCorner=new Vector2d(width, height);

		Vector2d vectorGeneratingMap = rightUpperCorner.substract(leftBottomCorner);
		this.area=vectorGeneratingMap.x*vectorGeneratingMap.y;



		this.leftBottomCornerOfJungle=this.leftBottomCorner.add(new Vector2d((int)(rightUpperCorner.x-jungleRatio*width)/2, (int)(rightUpperCorner.y-jungleRatio*height)/2));
		this.rightUpperCornerOfJungle = this.leftBottomCornerOfJungle.add(new Vector2d((int)Math.floor(jungleRatio*width)-1, (int)Math.floor(jungleRatio*height)-1));



		Vector2d vectorGeneratingJungle = rightUpperCornerOfJungle.substract(leftBottomCornerOfJungle);
		this.jungleArea=vectorGeneratingJungle.x*vectorGeneratingJungle.y;

		this.animalsOnPosition=new HashMap<Vector2d, TreeSet<Animal>>();
		this.plants=new HashMap<Vector2d, Plant>();

		while(howManyAnimalsOnStart>0)
		{
			Vector2d positionForNewAnimal = Vector2d.getRandomPointFromArea(leftBottomCorner, rightUpperCorner);
			if(!isOccupiedByAnimal(positionForNewAnimal))
			{
				Animal animal = new Animal(this, positionForNewAnimal, Parameters.START_ENERGY);
				insertAnimal(animal);
				animal.addObserver(this);
				howManyAnimalsOnStart--;
			}


		}

		for(int i=0; i<howManyPlantsOnStart/2; i++)
		{
			addPlants();
		}

	}
	
	
	public synchronized boolean place(Animal animal)
	{
		if(!canMoveTo(animal.getPosition())|| isOccupiedByAnimal(animal.getPosition()))return false;
		animal.addObserver(this);
		insertAnimal(animal);
		return true;
	}
	
	public synchronized Animal getAnimalAtPosition(Vector2d position)
	{
		return animalsOnPosition.get(position).first();
	}
	public synchronized boolean canMoveTo(Vector2d position)
	{
		if(position.x>rightUpperCorner.x || position.y>rightUpperCorner.y || position.x<leftBottomCorner.x || position.y<leftBottomCorner.y)return false;
		return true;
	}
	
	public synchronized boolean isOccupiedByAnimal(Vector2d position)
	{
		if(animalsOnPosition.containsKey(position))return true;
		return false;
	}
	

	private synchronized void removeDeadAnimals()
	{
		List<Vector2d> toRemove = new LinkedList<>();
		for(Vector2d position: animalsOnPosition.keySet())
		{
			Iterator<Animal> it = animalsOnPosition.get(position).iterator();
			while(it.hasNext())
			{
				Animal animal =  it.next();
				if(animal.getEnergy()<0)
				{
					it.remove();
					animal.removeObserver(this);
				}
			}


			if(animalsOnPosition.get(position).size()<=0)toRemove.add(position);
			
		}
		for(Vector2d position: toRemove)
		{
			animalsOnPosition.remove(position);
		}


	}
	private static HashMap<Vector2d, TreeSet<Animal>>  clone(HashMap<Vector2d, TreeSet<Animal>> hashMap)
	{
		HashMap<Vector2d, TreeSet<Animal>> result = new HashMap<Vector2d, TreeSet<Animal>>();
		for(Vector2d v: hashMap.keySet())
		{
			result.put(v, hashMap.get(v));

		}
		return result;
	}

	private synchronized void moveAnimals()
	{
		@SuppressWarnings("unchecked")
		HashMap<Vector2d, TreeSet<Animal>> animalsCopy = (HashMap<Vector2d, TreeSet<Animal>>) animalsOnPosition.clone();
		
		for(Vector2d position: animalsCopy.keySet())
		{
			Iterator<Animal> it = ((TreeSet<Animal>)(animalsCopy.get(position).clone())).iterator();
			while(it.hasNext())
			{
				Animal animal =  it.next();
				animal.move(MoveDirection.FORWARD);
			}
			
		}
	}
	
	private synchronized void feedAnimals()
	{

		for(Vector2d position: animalsOnPosition.keySet())
		{
			if(plants.containsKey(position))
			{
				Iterator<Animal> it = animalsOnPosition.get(position).iterator();
				int maximalEnergy=-1;
				int howManyAnimalsToShare=0;
				
				while(it.hasNext())
				{
					
					Animal animal = it.next();
					if(maximalEnergy==-1)maximalEnergy=animal.getEnergy();
					else if(maximalEnergy!=animal.getEnergy())break;
					howManyAnimalsToShare++;
				}
				
				Plant plant = plants.get(position);
				
				int energyPerAnimal = Parameters.PLANT_ENERGY/howManyAnimalsToShare;
				it = animalsOnPosition.get(position).iterator();
				for(int i=0; i<howManyAnimalsToShare; i++)
				{
					Animal animal = it.next();
					animal.fillEnergy(energyPerAnimal);
				}
				plants.remove(position);
				
			}
		}
	}
	
	private synchronized void handleProcreation() throws Exception
	{
		List<Animal> animalsToAdd = new LinkedList<>();
		for(Vector2d position: animalsOnPosition.keySet())
		{
			if(animalsOnPosition.get(position).size()>=2)
			{
					Iterator<Animal> it = animalsOnPosition.get(position).iterator();
					Animal firstParent = it.next();
					Animal secondParent=it.next();

					if(secondParent.getEnergy()>=Parameters.START_ENERGY*0.5)
					{
						Animal child = firstParent.procreate(secondParent);
						animalsToAdd.add(child);
						child.addObserver(this);
				}
			}
		}

		for(Animal a: animalsToAdd)
		{
			insertAnimal(a);

		}


		
		
		
	}
	
	private synchronized void addPlants()
	{
		Vector2d placeInJungle;
		int numberOfTries=0;
		do
		{
			 placeInJungle = Vector2d.getRandomPointFromArea(leftBottomCornerOfJungle, rightUpperCornerOfJungle);
			 numberOfTries++;
		}while((plants.containsKey(placeInJungle)||isOccupiedByAnimal(placeInJungle)) && numberOfTries<=jungleArea );
		
		
		plants.put(placeInJungle, new Plant(placeInJungle));
		Vector2d placeOutsideTheJungle;
		
		numberOfTries=0;
		do
		{
			placeOutsideTheJungle = Vector2d.getRandomPointFromArea(leftBottomCorner, rightUpperCorner);
			numberOfTries++;
		}while((placeOutsideTheJungle.isInside(leftBottomCornerOfJungle, rightUpperCornerOfJungle)
				||plants.containsKey(placeOutsideTheJungle)
				||isOccupiedByAnimal(placeOutsideTheJungle))
				&&  numberOfTries<=area
			);
		
		plants.put(placeOutsideTheJungle, new Plant(placeOutsideTheJungle));
		
		
	}
	
	public synchronized void nextDay() throws Exception
	{
		removeDeadAnimals();
		moveAnimals();
		feedAnimals();
		handleProcreation();
		addPlants();
			
	}
	
	public synchronized Vector2d getFreePositionInNeighbourhood(Vector2d position)
	{
		int howManyTries=0;
		Vector2d neighbourhood;
		do
		{
			neighbourhood = Vector2d.getRandomPointInNeighbourhood(position);
			howManyTries++;
		}while(isOccupiedByAnimal(neighbourhood)==true && howManyTries<=8);
			
			return neighbourhood;
	}
	
	
	private synchronized void insertAnimal(Animal animal)
	{
		SortedSet<Animal> set = this.animalsOnPosition.get(animal.getPosition());
		
		if(set==null)
		{
			    TreeSet<Animal> treeSet = new TreeSet<Animal>();
			    treeSet.add(animal);
				animalsOnPosition.put(animal.getPosition(), treeSet);
		}
		else 
		{
			set.add(animal);
		}
		
	}
	

	@Override
	public synchronized void positionChanged(IMapElement element, Vector2d oldPosition)
	{
		
			Iterator<Animal> it = animalsOnPosition.get(oldPosition).iterator();
			while(it.hasNext())
			{
				Animal animal = (Animal) it.next();
				if(animal.equals((Animal)element))
				{
					it.remove();
					break;
				}
			}
			
			if(animalsOnPosition.get(oldPosition).size()<=0)animalsOnPosition.remove(oldPosition);
			
			insertAnimal((Animal)element);
			

	}

	public synchronized int howManyAnimals()
	{

		return this.animalsOnPosition.keySet().size();


	}


	public synchronized int howManyPlants()
	{

		return this.plants.size();

	}

	public synchronized List<DrawableElement> getDrawables()
	{

		List<DrawableElement> result = new ArrayList<DrawableElement>();
		HashMap<Vector2d, TreeSet<Animal>> animalsCopy;



			for (Vector2d position : animalsOnPosition.keySet()) {

				result.add(new DrawableElement(position, DrawableType.ANIMAL));

			}

	for(Vector2d position: plants.keySet())
		{
			if(!animalsOnPosition.containsKey(position))
			{
				result.add(new DrawableElement(position, DrawableType.PLANT));
			}
		}

		return result;
	}
}

	
	


