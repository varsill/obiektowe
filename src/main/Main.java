package main;

import visualization.Frame;
import world.MapWithJungle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main
{
	static Status status;
	public static void main(String[] params) {

		try
		{
			Parameters.setParameters("parameters.json");
			status=new Status();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{

			MapWithJungle firstMap = new MapWithJungle(Parameters.MAP_WIDTH, Parameters.MAP_HEIGHT, (float)0.2,  Parameters.HOW_MANY_ANIMALS_ON_CREATION, Parameters.HOW_MANY_PLANTS_ON_CREATION);
			MapWithJungle secondMap =  new MapWithJungle(Parameters.MAP_WIDTH, Parameters.MAP_HEIGHT, (float)0.2, Parameters.HOW_MANY_ANIMALS_ON_CREATION,Parameters.HOW_MANY_PLANTS_ON_CREATION);

			Frame frame = new Frame(firstMap, secondMap, status, Parameters.WINDOW_WIDTH, Parameters.WINDOW_HEIGHT);

			while(true)
			{
				if(status.isFirstSceneOn) firstMap.nextDay();
				if(status.isSecondSceneOn) secondMap.nextDay();
				frame.updateFrame();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}


	/**
	 * Writes given content to specified file. If file doesn't exist, it will be created.
	 * If file already contains something, given content will be appended.
	 * @param filePath - path to file, where content should be saved
	 * @param content - text to be saved to the file
	 */
	public static void saveToFile(String filePath, String content) {
		File file1 = new File(filePath);
		try {
			if (!file1.exists()) file1.createNewFile();
			FileWriter fileWriter = new FileWriter(file1, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.newLine();
			bufferedWriter.close();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


