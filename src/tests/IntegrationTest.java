package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static main.MapDirection.*;

public class IntegrationTest {
    private Animal animal;


    @Test
    public void integrationTest1()
    {
        this.animal = new Animal(new RectangularMap(4, 4));
        assertTrue(animal.getOrientation()== NORTH && animal.getPosition().equals(new Vector2d(2, 2)));

        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.getOrientation()==NORTH);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 3)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.getOrientation()==NORTH);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 4)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.getOrientation()==NORTH);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 4)));
        animal.move(MoveDirection.RIGHT);
        assertTrue(animal.getOrientation()==EAST);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 4)));
        animal.move(MoveDirection.RIGHT);
        assertTrue(animal.getOrientation()==MapDirection.SOUTH);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 4)));

        String[] dir_strings={"b", "left", "forward", "f", "r"};

        MoveDirection[] dirs = OptionsParser.parse(dir_strings);

        for(MoveDirection d:dirs)
        {
            animal.move(d);
        }
        assertTrue(animal.getOrientation()==MapDirection.SOUTH);
        assertTrue(animal.getPosition().equals(new Vector2d(4, 4)));

    }

    @Test
    public void integrationTest2()
    {
        Animal bear=null;
        Animal lion = null;
        String[] dirs = {"f","b", "r", "l", "f", "l", "r", "f", "f", "f", "f", "f","b", "b", "b"};
        try {
            MoveDirection[] directions = OptionsParser.parse(dirs);
            IWorldMap map = new RectangularMap(10, 5);
            bear = new Animal(map);
            lion = new Animal(map, new Vector2d(3, 4));

            map.place(bear);
            map.place(lion);
            map.run(directions);
            

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        assertEquals(bear.getPosition(),new Vector2d(2, 3));
        assertEquals(bear.getOrientation(), SOUTH);
        assertEquals( lion.getPosition(), new Vector2d(3, 1));
        assertEquals(lion.getOrientation(), SOUTH);
    }





}
