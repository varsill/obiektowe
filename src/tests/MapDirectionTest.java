package tests;

import main.MapDirection;
import org.junit.*;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class MapDirectionTest {
    @Test
    public void testNext()
    {
        MapDirection e = MapDirection.EAST;
        MapDirection n= MapDirection.NORTH;
        MapDirection w = MapDirection.WEST;
        MapDirection s = MapDirection.SOUTH;
        assertEquals(s.next(), w);
        assertEquals(w.next(), n);
        assertEquals(n.next(), e);
        assertEquals(e.next(),s);

    }

    @Test
    public void testPrevious()
    {
        MapDirection e = MapDirection.EAST;
        MapDirection n= MapDirection.NORTH;
        MapDirection w = MapDirection.WEST;
        MapDirection s = MapDirection.SOUTH;
        assertEquals(s.previous(), e);
        assertEquals(e.previous(), n);
        assertEquals(n.previous(),w);
        assertEquals(w.previous(),s);

    }

}
