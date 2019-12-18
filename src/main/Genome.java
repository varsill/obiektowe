package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genome {
	private static final int GENETIC_CODE_LENGTH = 32;
	private static final int OPTIONS_PER_GENE=8;
	
	private final int[] geneticCode;

	
	public Genome()
	{
		this.geneticCode=new int [GENETIC_CODE_LENGTH];
		Random generator=new Random();
		for(int i=0; i<GENETIC_CODE_LENGTH; i++)
		{
			int gene = generator.nextInt()%OPTIONS_PER_GENE;
			this.geneticCode[i]=gene;
		}
		
		repair();
	}
	
	
	public Genome(int[] geneticCode)
	{
		this.geneticCode=geneticCode;
		repair();
	}
	
	
	public Genome(Genome firstParentGenome, Genome secondParentGenome)
	{
		Random generator=new Random();
		int splittingPos1 = generator.nextInt()%GENETIC_CODE_LENGTH;
		int splittingPos2 = generator.nextInt()%GENETIC_CODE_LENGTH;
		boolean swapFirstWithSecond = generator.nextBoolean();
		if(swapFirstWithSecond)
		{
			Genome buffer = firstParentGenome;
			firstParentGenome=secondParentGenome;
			secondParentGenome=buffer;
		}
		
		this.geneticCode=new int[GENETIC_CODE_LENGTH];
		
		for(int i=0; i<splittingPos1; i++)
		{
			this.geneticCode[i]=firstParentGenome.geneticCode[i];
		}
		for(int i=splittingPos1; i<splittingPos2; i++)
		{
			this.geneticCode[i]=secondParentGenome.geneticCode[i];
		}
		for(int i=splittingPos2; i<GENETIC_CODE_LENGTH; i++)
		{
			this.geneticCode[i]=firstParentGenome.geneticCode[i];
		}
		
		repair();
		
	}
	
	
	public int howManyTimesToRotate()
	{
		Random generator=new Random();
		int pos = generator.nextInt()%GENETIC_CODE_LENGTH;
		int gen = geneticCode[pos];
		return gen;
	}
	
	
	private void repair()
	{
		
		int[] numberOfOccurences = new int[OPTIONS_PER_GENE];
		for(int i=0; i<OPTIONS_PER_GENE; i++)
		{
			numberOfOccurences[i]++;
		}
		
		for(int i=0; i<OPTIONS_PER_GENE; i++)
		{
			if(numberOfOccurences[i]==0)
			{
				Random generator=new Random();
				int pos = generator.nextInt()%GENETIC_CODE_LENGTH;
				
				while(numberOfOccurences[geneticCode[pos]]<=1)
				{
					pos = generator.nextInt()%GENETIC_CODE_LENGTH;
				}
				
				numberOfOccurences[geneticCode[pos]]--;
				numberOfOccurences[i]++;
				geneticCode[pos]=i;
			}
		}
		
		Arrays.sort(this.geneticCode);
		
	}
}
