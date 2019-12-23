package world;

import main.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class Animal implements IMapElement, Comparable<Animal>{
    
	

	private Location location;
	private Genome genome;
	private int energy;
	public final Integer id;
    private List<IPositionChangeObserver> observers;
    private MapWithJungle mainMap;
    private int numberOfChildren=0;
	public int howManyDaysAlive=0;

	public Animal() throws Exception{
		this.id=-1;
	}
    public Animal(MapWithJungle map, Vector2d initialPosition, int initialEnergy) throws Exception
    {

        this.location=new Location(initialPosition);
        this.observers = new ArrayList<IPositionChangeObserver>();
        this.mainMap=map;
        this.energy=initialEnergy;
        this.id = AnimalIdGenerator.getInstance().getId();
        this.genome=new Genome();
        
    }
    
    
    public Animal(MapWithJungle map, Vector2d initialPosition, MapDirection initialOrientation, int initialEnergy, Genome initialGenome) throws Exception
    {
    	this(map, initialPosition, initialEnergy);
    	this.genome=initialGenome;
    }
    
    @Override
    public void finalize()
    {
    	AnimalIdGenerator.getInstance().freeId(this.id);
    }
    
   
    public void addObserver(IPositionChangeObserver observer)
    {
    	observers.add(observer);
    }
    
    public void removeObserver(IPositionChangeObserver observer)
    {
    	observers.remove(observer);
    }
    
    
    public boolean equals(Animal other)
    {
        if(this.id.equals(other.id))return true;
        return false;

    }

    public MapDirection getOrientation()
    {
        return this.location.getOrientation();
    }

    public Vector2d getPosition()
    {
        return this.location.getPosition();
    }

    public void move(MoveDirection dir)
    {
    	chooseOrientation();
    	Vector2d oldPosition = this.location.getPosition();
    	this.location.move(dir);
    	useEnergy(Parameters.MOVE_ENERGY);
    	if(this.mainMap.canMoveTo(this.getPosition())) positionChanged(oldPosition, this.location.getPosition());
    	else this.location.setPosition(oldPosition);

    }
    
    public int getEnergy()
    {
    	return this.energy;
    }
    
    public Animal procreate(Animal partner) throws Exception
    {
    	int energyGivenByMe = (int) (this.energy*0.25);
    	this.useEnergy(energyGivenByMe);
    	
    	int energyGivenByPartner = (int) (partner.energy*0.25);
    	partner.useEnergy(energyGivenByPartner);
    	
    	Animal child = new Animal(this.mainMap,((MapWithJungle)this.mainMap).getFreePositionInNeighbourhood(this.getPosition()), energyGivenByMe+energyGivenByPartner);

    	this.numberOfChildren++;
    	partner.numberOfChildren++;
    	return child;
    }
    
    private void chooseOrientation()
    {
    	this.location.rotate45DegClockwise(this.genome.howManyTimesToRotate());
    }
	
    
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
    	for(IPositionChangeObserver observer: observers)
    	{
    		observer.positionChanged(this, oldPosition);
    	}
    }

	@Override
	public int compareTo(Animal other) {
		
		if(this.energy<other.energy)return -1;
		else if(this.energy==other.energy)
		{
				if(this.id<other.id) return -1;
				else if (this.id>other.id)return 1;
				else return 0;
		}
		else return 1;
	}
	

	public int getID()
	{
		return this.id;
	}

	public String getGenome()
	{
		return this.genome.toString();
	}

	public int getNumberOfChildren()
	{
		return this.numberOfChildren;
	}


	public void useEnergy(int energy)
	{
		this.energy-=energy;
	}
	
	public void fillEnergy(int energy)
	{
		this.energy+=energy;
	}
	
	
	 public static class AnimalIdGenerator implements IdGenerator
		{
			 private static final int MAX_NO_OF_IDS = 30000;
			 private static int offset = 0;
			 private static LinkedList<Integer> ids;
			//Singleton implementation
			
			 private AnimalIdGenerator()
			 {
				offset = 0;
			    ids = new LinkedList<Integer>(); 
			 } 
		    
		    private static class SingletonHelper
		    {
		        private static final AnimalIdGenerator INSTANCE = new AnimalIdGenerator();
		    }
		    
		    /**
		     * 
		     * @return instance of singleton object of class MainIdGenerator
		     */
		    public static AnimalIdGenerator getInstance(){
		        return SingletonHelper.INSTANCE;
		    }
		    



			//Methods
			@Override
			public Integer getId() throws Exception
			{
				Integer result = ids.pollFirst();//returns null if the list is empty
				if(result==null)
				{
					if(offset == MAX_NO_OF_IDS)
						throw new Exception("Error. There are no free ids left. ");
				
					result=offset;
					offset=offset+1;
				}
				return result; 
				
			}
			
			
			@Override
			public void freeId(Integer id)
			{
				if(id>=offset)return;
				int index = ids.indexOf(id);
				if(index!=-1)return;//number is already on the ids list

				ids.addFirst(id);
				
			}
			
			@Override
			public boolean isOccupied(Integer id) {
				if(id>=offset) return false;
				int index = ids.indexOf(id);
				if(index==-1)return false;//number is already on the ids list
				return true;
			}
			
			private void optimize()
			{
				Collections.sort(ids);
				int x = ids.getLast();
				while(offset==x)
				{
					offset=offset-1;
					x=ids.poll();
				}
			}
		}
   

}
