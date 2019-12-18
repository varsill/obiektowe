package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap{
	
    public RectangularMap(int width, int height)
    {
    	super(width, height);
        
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(!super.canMoveTo(position)|| position.x<0 || position.x>rightTop.x || position.y<0||position.y>rightTop.y) return false;
        return true;
    }

    @Override
    public boolean place(Animal animal) {
    	if(animal==null) return false;
        if(canMoveTo(animal.getPosition()))
        {
            higherPriorityElements.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        return false;
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



   
}
