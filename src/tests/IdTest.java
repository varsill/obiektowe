package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.gen5.api.Assertions.assertThrows;


import main.Parameters;
import org.junit.Test;

import world.Animal;
import world.IdGenerator;


public class IdTest extends Animal{

    public IdTest() throws Exception {

    }

    @Test
    public void testIdGenerator() throws Exception
    {
        IdGenerator generator = Animal.AnimalIdGenerator.getInstance();
        int x=generator.getId(); //0
        assertEquals(x, 0);

        Integer y = generator.getId();
        assertEquals(y, new Integer(1));//1

        generator.getId(); //2
        generator.getId();//3
        generator.freeId(x);
        generator.freeId(y);
        generator.freeId(2);

        y = generator.getId(); //2
        assertEquals(y, new Integer(2));

        generator.freeId(0);
        generator.freeId(x);

        y = generator.getId(); //1
        assertEquals(y, new Integer(1));

        assertThrows(Exception.class, () ->
        {
            for(int i = 0; i<= Parameters.HOW_MANY_IDS; i++) generator.getId();
        });


    }
}