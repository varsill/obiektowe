package main;

import java.util.Random;

public class Vector2d extends Object {
   final public int x;
   final public int y;
   	
    public Vector2d(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString()
    {
        return new String("("+Integer.toString(x)+","+Integer.toString(y)+")");
    }

    public boolean precedes(Vector2d other)
    {
        if(this.x<=other.x && this.y<=other.y)return true;
        return false;
    }

    public boolean follows(Vector2d other)
    {
        if(this.x>=other.x && this.y>=other.y) return true;
        return false;
    }

    public Vector2d upperRight(Vector2d other)
    {
        int x, y;
        if(this.x>=other.x)x=this.x;
        else x=other.x;
        if(this.y>=other.y)y=this.y;
        else y=other.y;

        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        int x, y;
        if(this.x>=other.x)x=other.y;
        else x=this.x;
        if(this.y>=other.y)y=other.y;
        else y=this.y;

        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x+other.x, this.y+other.y);
    }

    public Vector2d substract(Vector2d other)
    {
        return new Vector2d(this.x-other.x, this.y-other.y);
    }

    @Override
    public boolean equals(Object other)
    {
        if(!other.getClass().equals(this.getClass())) return false;
        Vector2d c = (Vector2d)other;
        if(this.x==c.x && this.y==c.y)return true;
        return false;
    }

    public Vector2d opposite()
    {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode()
    {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
    
    public boolean isInside(Vector2d leftBottomCorner, Vector2d rightUpperCorner)
    {
    	if(this.x>=leftBottomCorner.x && this.x<=rightUpperCorner.x && this.y>=leftBottomCorner.y && this.y<=rightUpperCorner.y)return true;
    	return false;
    }
    
    public static Vector2d getRandomPointFromArea(Vector2d leftBottomCorner, Vector2d rightUpperCorner)
    {
    	Random generator = new Random();
    	int x = leftBottomCorner.x+(Math.abs(generator.nextInt())%(rightUpperCorner.x-leftBottomCorner.x+1));
    	int y = leftBottomCorner.y+(Math.abs(generator.nextInt())%(rightUpperCorner.y-leftBottomCorner.y+1));
    	
    	return new Vector2d(x,y);
    }
    public static Vector2d getRandomPointInNeighbourhood(Vector2d position)
    {

    	Location location = new Location(position);
    	location.move(MoveDirection.FORWARD);
    	return location.getPosition();
    }
}


