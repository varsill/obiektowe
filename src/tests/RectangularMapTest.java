package tests;

import main.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static main.MapDirection.NORTH;
import static main.MapDirection.SOUTH;


public class RectangularMapTest {
    private IWorldMap map;
    private static final int N = 10;
    @Before
    public void initializeMap() throws Exception
    {
    	
        this.map=new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));

    }


    @Test
    public void canMoveToTest() throws Exception
    {
    	
        assertTrue(map.canMoveTo(new Vector2d(2, 3))==true);
        assertTrue(map.canMoveTo(new Vector2d(1, 0))==true);
        assertTrue(map.canMoveTo(new Vector2d(2, 2))==false);
        assertTrue(map.canMoveTo(new Vector2d(3, 4))==false);
        assertTrue(map.canMoveTo(new Vector2d(100, -100))==false);

    }

    @Test
    public void placeTest() throws Exception
    {
    	Animal lion = new Animal(map, new Vector2d(1,0));
        assertTrue(map.place(lion)==true);
        Animal bear = new Animal(map, new Vector2d(2, 3));
        assertTrue(map.place(bear)==true);
        Animal deer = null;
        try
        {
            deer = new Animal(map, new Vector2d(2,2));
        }catch(Exception e)
        {

        }
        	
        assertTrue(map.place(deer)==false);
       
    }

    @Test
    public void runTest() throws Exception
    {

        Animal bear=null;
        Animal lion = null;
        String[] dirs = {"f","b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f"};
        try {
            MoveDirection[] directions = OptionsParser.parse(dirs);
            
            bear = (Animal) map.objectAt(new Vector2d(2, 2));
            lion = (Animal) map.objectAt(new Vector2d(3, 4));

            map.run(directions);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        assertEquals(bear.getPosition(),new Vector2d(2, 0));
        assertEquals(bear.getOrientation(), SOUTH);
        assertEquals( lion.getPosition(), new Vector2d(3,5));
        assertEquals(lion.getOrientation(), NORTH);
    }

    @Test
    public void isOccupiedTest()
    {
        assertTrue(map.isOccupied(new Vector2d(2, 3))==false);
        assertTrue(map.isOccupied(new Vector2d(1, 0))==false);
        assertTrue(map.isOccupied(new Vector2d(2, 2))==true);
        assertTrue(map.isOccupied(new Vector2d(3, 4))==true);
    }

    @Test
    public void objectAtTest()
    {
      
       assertTrue(map.objectAt(new Vector2d(2, 3))==null);
       assertTrue(map.objectAt(new Vector2d(2, 2)) instanceof Animal);
       assertTrue(map.objectAt(new Vector2d(3, 4)) instanceof Animal);
    }


}