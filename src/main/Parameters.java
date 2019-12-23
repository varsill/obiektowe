package main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Parameters {
	public static int MAP_WIDTH;
	public static int MAP_HEIGHT;
	public static float JUNGLE_RATIO;
	public static int START_ENERGY;
	public static int MOVE_ENERGY;
	public static int PLANT_ENERGY;
	public static String STATISTICS_FILE_PATH="statistics.txt";
	public static int HOW_MANY_IDS=30000;
	public static int HOW_MANY_ANIMALS_ON_CREATION=100;
	
	public static void setParameters(int mapWidth, int mapHeight, int jungleRatio,int startEnergy, int moveEnergy, int plantEnergy)
	{
		MAP_WIDTH = mapWidth;
		MAP_HEIGHT = mapHeight;
		JUNGLE_RATIO=jungleRatio;
		START_ENERGY=startEnergy;
		MOVE_ENERGY=moveEnergy;
		PLANT_ENERGY=plantEnergy;
	}
	
	public static void setParameters(String pathToConfigurationFile) throws IOException, ParseException {
		 Object obj = new JSONParser().parse(new FileReader(pathToConfigurationFile));
	     JSONObject jo = (JSONObject) obj;
	     MAP_WIDTH = (int) (long) jo.get("width");
	     MAP_HEIGHT = (int)(long) jo.get("height");
		 JUNGLE_RATIO = (float)(double)jo.get("jungleRatio");

	     START_ENERGY = (int) (long)jo.get("startEnergy");
	     MOVE_ENERGY = (int)(long) jo.get("moveEnergy");
		 PLANT_ENERGY = (int)(long) jo.get("plantEnergy");
	}
	
	
}
