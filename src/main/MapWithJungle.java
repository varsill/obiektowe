package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapWithJungle implements IPositionChangeObserver{

	private HashMap<Vector2d, TreeSet<Animal>> animalsOnPosition;
	private HashMap<Vector2d, Plant> plants;
	private Vector2d leftBottomCorner;
	private Vector2d rightUpperCorner;
	private Vector2d leftBottomCornerOfJungle;
	private Vector2d rightUpperCornerOfJungle;
	private int jungleArea;
	private int area;
	
	
	
	public MapWithJungle(Vector2d leftBottomCorner, Vector2d rightUpperCorner, Vector2d leftBottomCornerOfJungle, Vector2d rightUpperCornerOfJungle)
	{
		this.leftBottomCorner=leftBottomCorner;
		this.rightUpperCorner=rightUpperCorner;
		Vector2d vectorGeneratingMap = rightUpperCorner.substract(leftBottomCornerOfJungle);
		this.area=vectorGeneratingMap.x*vectorGeneratingMap.y;
		this.leftBottomCornerOfJungle=leftBottomCornerOfJungle;
		this.rightUpperCornerOfJungle = rightUpperCornerOfJungle;
		Vector2d vectorGeneratingJungle = rightUpperCornerOfJungle.substract(leftBottomCornerOfJungle);
		this.jungleArea=vectorGeneratingJungle.x*vectorGeneratingJungle.y;
	}
	
	
	public boolean place(Animal animal)
	{
		if(!canMoveTo(animal.getPosition())|| isOccupiedByAnimal(animal.getPosition()))return false;
		animal.addObserver(this);
		insertAnimal(animal);
		return true;
	}
	
	
	public boolean canMoveTo(Vector2d position)
	{
		if(position.x>rightUpperCorner.x || position.y>rightUpperCorner.y || position.x<leftBottomCorner.x || position.y<leftBottomCorner.y)return false;
		return true;
	}
	
	public boolean isOccupiedByAnimal(Vector2d position) 
	{
		if(animalsOnPosition.containsKey(position))return true;
		return false;
	}
	

	private void removeDeadAnimals()
	{
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
			
			if(animalsOnPosition.get(position).size()<=0)animalsOnPosition.remove(position);
			
		}
	}
	
	private void moveAnimals()
	{
		@SuppressWarnings("unchecked")
		HashMap<Vector2d, TreeSet<Animal>> animalsCopy = (HashMap<Vector2d, TreeSet<Animal>>) animalsOnPosition.clone();
		
		for(Vector2d position: animalsCopy.keySet())
		{
			Iterator<Animal> it = animalsCopy.get(position).iterator();
			while(it.hasNext())
			{
				Animal animal =  it.next();
				animal.move(MoveDirection.FORWARD);
			}
			
		}
	}
	
	private void feedAnimals()
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
				
				int energyPerAnimal = plant.howNutrious/howManyAnimalsToShare;
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
	
	private void handleProcreation() throws Exception
	{
		for(Vector2d position: animalsOnPosition.keySet())
		{
			if(animalsOnPosition.get(position).size()>=2)
			{
					Iterator<Animal> it = animalsOnPosition.get(position).iterator();
					Animal firstParent = it.next();
					Animal secondParent=it.next();
				
					if(secondParent.getEnergy()>=Animal.MINIMUM_ENERGY_FOR_PROCREATION)
					{
						Animal child = firstParent.procreate(secondParent);
						child.addObserver(this);
						if(animalsOnPosition.get(child.getPosition())==null)
						{
							TreeSet<Animal> tree = new TreeSet<Animal>();
					    	tree.add(child);
					    	animalsOnPosition.put(child.getPosition(), tree);
						}
						else
						{
							animalsOnPosition.get(child.getPosition()).add(child);
						}
				}
			}
		}
		
		
		
	}
	
	private void addPlants()
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
	
	public void nextDay() throws Exception
	{
		removeDeadAnimals();
		moveAnimals();
		feedAnimals();
		handleProcreation();
		addPlants();
			
	}
	
	public Vector2d getFreePositionInNeighbourhood(Vector2d position)
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
	
	
	private void insertAnimal(Animal animal)
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
	public void positionChanged(IMapElement element, Vector2d oldPosition)
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
}

	
	


