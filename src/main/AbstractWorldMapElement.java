package main;

public abstract class AbstractWorldMapElement implements IMapElement {
	protected Vector2d position;
	
	
	public AbstractWorldMapElement()
	{
		this.position=new Vector2d(0, 0);
	}
	
	public AbstractWorldMapElement(Vector2d pos)
	{
		this.position=pos;
	}
	
	@Override
	public Vector2d getPosition()
	{
		return this.position;
	}
}
