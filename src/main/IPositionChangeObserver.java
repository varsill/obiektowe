package main;

public interface IPositionChangeObserver {
	void positionChanged(IMapElement element, Vector2d oldPosition);
	
}
