package world;

import main.Vector2d;

public interface IMapElement {
	/**
	 *
	 * @return vector representing the position of element on the map, on which the element is set
	 */
	Vector2d getPosition();
}
