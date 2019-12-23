package world;

import main.Vector2d;

public interface IPositionChangeObserver {
	/**
	 *
	 * @param element - map element, which position has changed
	 * @param oldPosition - old position of element, before its position has changed
	 */
	void positionChanged(IMapElement element, Vector2d oldPosition);
	
}
