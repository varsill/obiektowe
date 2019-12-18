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

public class GrassFieldTest {
    private IWorldMap map;
    private static final int N = 10;
    @Before
    public void initializeMap() throws Exception
    {
    	
        List<Grass> grassList = new ArrayList<Grass>(){{
            add(new Grass(new Vector2d(1, 0)));
            add(new Grass(new Vector2d(3, 4)));
            add(new Grass(new Vector2d(3, 0)));
            add( new Grass(new Vector2d(5, 0)));
            add(new Grass(new Vector2d(2, -1)));
            add(new Grass(new Vector2d(1, 0)));
        }};


        this.map=new GrassField(grassList, 5, 6);
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
        assertTrue(map.canMoveTo(new Vector2d(100, -100)));

    }

    @Test
    public void placeTest() throws Exception
    {
    	Animal lion = new Animal(map, new Vector2d(1,0));
        assertTrue(map.place(lion)==true);
        Animal bear = new Animal(map, new Vector2d(100, -100));
        assertTrue(map.place(bear)==true);
        Animal deer = null;
        deer = new Animal(map, new Vector2d(2,2));
    }

    @Test
    public void runTest() throws Exception
    {
        Animal bear=null;
        Animal lion = null;
        String[] dirs = {"f","b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        
        try {
            MoveDirection[] directions = OptionsParser.parse(dirs);
            bear = (Animal) map.objectAt(new Vector2d(2, 2));
            lion = (Animal) map.objectAt(new Vector2d(3,4));	
            map.run(directions);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        assertEquals(bear.getPosition(),new Vector2d(2, -1));
        assertEquals(bear.getOrientation(), SOUTH);
        assertEquals( lion.getPosition(), new Vector2d(3, 7));
        assertEquals(lion.getOrientation(), NORTH);
    }

    @Test
    public void isOccupiedTest()
    {
        assertTrue(map.isOccupied(new Vector2d(2, 3))==false);
        assertTrue(map.isOccupied(new Vector2d(1, 0))==true);
        assertTrue(map.isOccupied(new Vector2d(2, 2))==true);
        assertTrue(map.isOccupied(new Vector2d(3, 4))==true);
    }

    @Test
    public void objectAtTest()
    {
      
       assertTrue(map.objectAt(new Vector2d(2, 3))==null);
       assertTrue(map.objectAt(new Vector2d(1, 0))instanceof Grass);
       assertTrue(map.objectAt(new Vector2d(2, 2)) instanceof Animal);
       assertTrue(map.objectAt(new Vector2d(3, 4)) instanceof Animal);
    }


}
