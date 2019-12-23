package world;

import main.Vector2d;

public interface IPositionChangeObserver {
	void positionChanged(IMapElement element, Vector2d oldPosition);
	
}
