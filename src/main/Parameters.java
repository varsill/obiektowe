package main;

public class Parameters {
	final int MAP_SIZE_X;
	final int MAP_SIZE_Y;
	final int JUNGLE_SIZE_X;
	final int JUNGLE_SIZE_Y;
	
	
	Parameters(int mapSizeX, int mapSizeY, int jungleSizeX, int jungleSizeY)
	{
		MAP_SIZE_X=mapSizeX;
		MAP_SIZE_Y=mapSizeY;
		JUNGLE_SIZE_X=jungleSizeX;
		JUNGLE_SIZE_Y=jungleSizeY;
	}
	
	Parameters(String pathToConfigurationFile)
	{
		
	}
	
	
}
