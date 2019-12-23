package main;

import main.MapDirection;
import main.MoveDirection;
import main.Vector2d;

import java.util.Random;

public class Location {
	private MapDirection orientation;
	private Vector2d position;


	public Location(Vector2d initialPosition)
	{
		this.position=initialPosition;
		getRandomOrientation();
	}

	public Location(Vector2d initialPosition, MapDirection initialOrientation)
	{
		this.position=initialPosition;
		this.orientation=initialOrientation;
	}
	
	
	public void rotate45DegClockwise(int howManyTimes)
	{
		for(int i=0; i<howManyTimes; i++)
		{
			this.orientation=this.orientation.next();
		}
	}
	
	public Vector2d getPosition()
	{
		return new Vector2d(this.position.x, this.position.y);
	}

	public void setPosition(Vector2d position)
	{
		this.position=position;
	}
	
	public MapDirection getOrientation()
	{
		return this.orientation;
	}

	public void move(MoveDirection moveDirection)
	{
		if(moveDirection==MoveDirection.FORWARD)
		{
			this.position=this.position.add(this.orientation.toUnitVector());
		}
		else if(moveDirection==MoveDirection.BACKWARD) {
			this.position = this.position.add(this.orientation.toUnitVector().opposite());
		}
	}
	
	
	public void move()
	{
		move(MoveDirection.FORWARD);
	}

	private void getRandomOrientation()
	{
		Random generator = new Random();
		this.orientation = MapDirection.NORTH;
		int howManyTimesToRotate = Math.abs(generator.nextInt())%8;
		rotate45DegClockwise(howManyTimesToRotate);
	}
	
}
