package main;

public class Plant extends AbstractWorldMapElement{
	

	    public Plant(main.Vector2d position)
	    {
	        super(position);
	    }


	    public Vector2d getPosition()
	    {
	        return this.position;
	    }

	    public String toString()
	    {
	        return "*";
	    }
}
