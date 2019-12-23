package main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Parameters {

	public static int MAP_WIDTH=100;
	public static int MAP_HEIGHT=100;
	public static float JUNGLE_RATIO=(float)0.2;
	public static int START_ENERGY=50;
	public static int MOVE_ENERGY=1;
	public static int PLANT_ENERGY=10;

	public static String STATISTICS_FILE_PATH="statistics.txt";
	public static int HOW_MANY_IDS=30000;
	public static int HOW_MANY_ANIMALS_ON_CREATION=100;
	public static int HOW_MANY_PLANTS_ON_CREATION=1000;
	public static int WINDOW_WIDTH=1200;
	public static int WINDOW_HEIGHT=800;

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
