package tests;

import org.junit.Test;
import world.Genome;

import static org.junit.Assert.assertTrue;

public class GenomeTest {

    private boolean isGenomeComplete(Genome genome)
    {
        String genomeString = genome.toString();
        for(int i=0; i<8; i++)
        {
            if(!genomeString.contains(new Integer(i).toString())) return false;
        }
        int prev=-1;
        for(int i=0; i<genomeString.length(); i++)
        {
            if(Integer.valueOf(genomeString.charAt(i))<prev)return false;
            prev=Integer.valueOf(genomeString.charAt(i));
        }
        return true;
    }

    @Test
    public void testGenomeGeneration()
    {
        Genome genome = new Genome();
        assertTrue(isGenomeComplete(genome));
    }


    @Test
    public void testGenomeMixing()
    {
        Genome a = new Genome();
        Genome b = new Genome();
        Genome c = new Genome(a, b);
        assertTrue(isGenomeComplete(c));
    }
}
