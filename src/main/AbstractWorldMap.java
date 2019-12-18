package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractWorldMap implements IWorldMap{
	 protected MapVisualizer visualizer;
     protected Vector2d leftBottom;
     protected  Vector2d rightTop;
     protected HashMap<Vector2d, IMapElement> higherPriorityElements;
     protected HashMap<Vector2d, IMapElement> lowerPriorityElements;
   
     public AbstractWorldMap()
    {
    	
        this.visualizer=new MapVisualizer(this);
        this.higherPriorityElements = new LinkedHashMap<>();
        this.lowerPriorityElements = new LinkedHashMap<>();
    }
    
    public AbstractWorldMap(int width, int height)
    {
    	this.leftBottom=new Vector2d(0, 0);
    	this.rightTop=new Vector2d(width, height);
    	this.visualizer=new MapVisualizer(this);
        this.higherPriorityElements = new LinkedHashMap<>();
        this.lowerPriorityElements = new LinkedHashMap<>();
    }
    
    @Override
    public boolean isOccupied(Vector2d position) {
        if(objectAt(position)!=null)return true;
        return false;
    }
    
    @Override
    public boolean canMoveTo(Vector2d position) {
    	if(objectAt(position) instanceof Animal)return false;
    	
        return true;
    }
    
    @Override
    public Object objectAt(Vector2d position) {
        IMapElement element = (IMapElement) higherPriorityElements.get(position);
        if(element!=null)return element;
        return lowerPriorityElements.get(position);
    }
    
    @Override
    public String toString()
    {
        return this.visualizer.draw(this.leftBottom, this.rightTop);
    }
    
    
    @Override
    public void positionChanged(Vector2d old_position, Vector2d new_position)
    {
    	Animal element = (Animal) higherPriorityElements.remove(old_position);
    	if(canMoveTo(new_position))higherPriorityElements.put(new_position, element);
    }
}
