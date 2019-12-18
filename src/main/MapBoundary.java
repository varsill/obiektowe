package main;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

	
	private SortedSet<Vector2d> elementsX;
	private SortedSet<Vector2d> elementsY;
	
	
	public MapBoundary()
	{
		this.elementsX=new TreeSet<Vector2d>(new Comparator<Vector2d>()
				{

					@Override
					public int compare(Vector2d position1, Vector2d position2) {
						
						
						if(position1.x>position2.x)return 1;
						else if (position1.x<position2.x)return -1;
						else
						{
							if(position1.y>position2.y)return 1;
							else if (position1.y<position2.y)return -1;
							else return 0;
						}
					}
					
				}
		);
		
		
		
		this.elementsY=new TreeSet<Vector2d>(new Comparator<Vector2d>()
		{

			@Override
			public int compare(Vector2d position1, Vector2d position2) {
				
				if(position1.y>position2.y)return 1;
				else if (position1.y<position2.y)return -1;
				else
				{
					if(position1.x>position2.x)return 1;
					else if (position1.x<position2.x)return -1;
					else return 0;
				}
			}
			
		}
);
	}
	
	
	public void addElement(IMapElement element)
	{
		elementsX.add(element.getPosition());
		elementsY.add(element.getPosition());
	}
	
	@Override
	public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
		
		elementsX.remove(oldPosition);
		elementsX.add(newPosition);
		elementsY.remove(oldPosition);
		elementsY.add(newPosition);
		
	}
	
	public Vector2d getLeftBottom()
	{
		return new Vector2d(elementsX.first().x, elementsY.first().y);
	}
	
	public Vector2d getRightTop()
	{
		return new Vector2d(elementsX.last().x, elementsY.last().y);
	}
	
	
	

}
