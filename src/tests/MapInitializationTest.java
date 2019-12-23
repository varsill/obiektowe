package tests;

import main.Parameters;
import org.junit.Before;
import org.junit.Test;
import world.MapWithJungle;

import static org.junit.Assert.assertEquals;

public class MapInitializationTest {


    @Before
    public void initialize()
    {
        try
        {
            Parameters.setParameters("parameters.json");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testInitialization() throws Exception
    {
                MapWithJungle map = new MapWithJungle(100, 100, (float)0.2, 100, 100);

                assertEquals(map.howManyPlants(), 100);
                assertEquals(map.howManyAnimals(), 100);

    }
}
