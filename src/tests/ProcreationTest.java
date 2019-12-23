package tests;

import main.Parameters;
import main.Vector2d;
import org.junit.Before;
import org.junit.Test;
import world.Animal;
import world.MapWithJungle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProcreationTest {

    private MapWithJungle map=null;

    @Before public void initialize()
    {
        try
        {
            Parameters.setParameters("parameters.json");
            this.map=new MapWithJungle(Parameters.MAP_WIDTH, Parameters.MAP_HEIGHT, 0,  0, 0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    @Test
    public void testProcreation() throws Exception
    {

            Animal a = new Animal(map, new Vector2d(1, 1), 100);
            Animal b = new Animal(map, new Vector2d(1, 1), 100);
            Animal c = a.procreate(b);
            assertEquals(c.getEnergy(),50);
            assertEquals(a.getEnergy(), 75);
            assertEquals(b.getEnergy(), 75);
            assertTrue(c.getPosition().subtract(a.getPosition()).length()<2.0);




    }


}
