package world;

import java.util.Arrays;
import java.util.Random;

public class Genome {
	private static final int GENETIC_CODE_LENGTH =32;
	private static final int OPTIONS_PER_GENE=8;
	
	private final int[] geneticCode;

// 		 ██████╗ ██████╗ ███╗   ██╗███████╗████████╗██████╗ ██╗   ██╗ ██████╗████████╗ ██████╗ ██████╗ ███████╗
//		 ██╔════╝██╔═══██╗████╗  ██║██╔════╝╚══██╔══╝██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝
//		 ██║     ██║   ██║██╔██╗ ██║███████╗   ██║   ██████╔╝██║   ██║██║        ██║   ██║   ██║██████╔╝███████╗
//		 ██║     ██║   ██║██║╚██╗██║╚════██║   ██║   ██╔══██╗██║   ██║██║        ██║   ██║   ██║██╔══██╗╚════██║
//		 ╚██████╗╚██████╔╝██║ ╚████║███████║   ██║   ██║  ██║╚██████╔╝╚██████╗   ██║   ╚██████╔╝██║  ██║███████║
//		 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝  ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝
	 /**
	 * creates randomly generated genome
	 */
	public Genome()
	{
		this.geneticCode=new int [GENETIC_CODE_LENGTH];
		Random generator=new Random();
		for(int i=0; i<GENETIC_CODE_LENGTH; i++)
		{
			int gene =  Math.abs(generator.nextInt())%OPTIONS_PER_GENE;
			this.geneticCode[i]=gene;
		}
		
		repair();
	}


	/**
	 *  Creates new genome based on parents genome
	 * @param firstParentGenome - genome of first parent
	 * @param secondParentGenome - genome of second parent
	 */
	public Genome(Genome firstParentGenome, Genome secondParentGenome)
	{
		Random generator=new Random();
		int splittingPos1 = Math.abs(generator.nextInt())%GENETIC_CODE_LENGTH;
		int splittingPos2 = Math.abs(generator.nextInt())%GENETIC_CODE_LENGTH;
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

//	 		 ██████╗ ████████╗██╗  ██╗███████╗██████╗     ███╗   ███╗███████╗████████╗██╗  ██╗ ██████╗ ██████╗ ███████╗
//			 ██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██╔══██╗    ████╗ ████║██╔════╝╚══██╔══╝██║  ██║██╔═══██╗██╔══██╗██╔════╝
//			 ██║   ██║   ██║   ███████║█████╗  ██████╔╝    ██╔████╔██║█████╗     ██║   ███████║██║   ██║██║  ██║███████╗
//			 ██║   ██║   ██║   ██╔══██║██╔══╝  ██╔══██╗    ██║╚██╔╝██║██╔══╝     ██║   ██╔══██║██║   ██║██║  ██║╚════██║
//			 ╚██████╔╝   ██║   ██║  ██║███████╗██║  ██║    ██║ ╚═╝ ██║███████╗   ██║   ██║  ██║╚██████╔╝██████╔╝███████║
//			 ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝

	/**
	 *
	 * @return how many times should an Genome owner rotate, based on its genome specification
	 */
	public int howManyTimesToRotate()
	{
		Random generator=new Random();
		int pos = Math.abs(generator.nextInt())%GENETIC_CODE_LENGTH;
		int gen = geneticCode[pos];
		return gen;
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder  = new StringBuilder();
		for(int i=0; i<GENETIC_CODE_LENGTH; i++)
		{
			stringBuilder.append(geneticCode[i]);
		}
		return stringBuilder.toString();
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
				int pos =  Math.abs(generator.nextInt())%GENETIC_CODE_LENGTH;

				while(numberOfOccurences[geneticCode[pos]]<=1)
				{
					pos = Math.abs(generator.nextInt())%GENETIC_CODE_LENGTH;
				}

				numberOfOccurences[geneticCode[pos]]--;
				numberOfOccurences[i]++;
				geneticCode[pos]=i;
			}
		}
		Arrays.sort(this.geneticCode);
	}
}
