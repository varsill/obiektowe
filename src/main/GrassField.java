package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassField extends AbstractWorldMap {

	
	private MapBoundary mapBoundary;
	
    public GrassField(List<Grass> grassList, int width, int height)
    {
    	
    	super(width, height);
    	mapBoundary = new MapBoundary();
        for(Grass g: grassList)
        	{
        		lowerPriorityElements.put(g.getPosition(), g);
        	}
    }
    
    public GrassField(int n)
    {
    	super((int)Math.ceil(Math.sqrt(n*10)), (int)Math.ceil(Math.sqrt(n*10)));
    	mapBoundary = new MapBoundary();
    	int width = this.rightTop.x;
    	int height = this.rightTop.y;

        Random generator = new Random();
        while(n>0)
        {
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);

            Vector2d pos = new Vector2d(x, y);
            if(!isOccupied(pos))
            {
                lowerPriorityElements.put(pos, new Grass(pos));
                n--;
            }
        }
    }
    

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException{
    	if(animal==null)return false;
        if(canMoveTo(animal.getPosition()))
        {
           
        	higherPriorityElements.put(animal.getPosition(), animal);
            animal.addObserver(this);            
            mapBoundary.addElement(animal);
            animal.addObserver(mapBoundary);
            return true;
        }
        throw new IllegalArgumentException("Couldn't place animal on position: "+animal.getPosition().toString());
    }

    @Override
    public void run(MoveDirection[] directions) {
        int  i = 0;
        while(i<directions.length)
        {
        	List<Animal> animals_list = new ArrayList<Animal>((Collection<? extends Animal>) higherPriorityElements.values());
        	for(Animal a: animals_list )
        	{
        	
        		 a.move(directions[i]);
                 i++; 
                 if(i>=directions.length)return;
        	}
        }
       
    }
    
    
    @Override
    public String toString()
    {
    	this.rightTop=mapBoundary.getRightTop();
    	this.leftBottom=mapBoundary.getLeftBottom();
    	return super.toString();
    }
    

   

}
