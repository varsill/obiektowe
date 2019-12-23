package main;

import visualization.Frame;
import world.MapWithJungle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main
{
	static Status status=new Status();
	public static void main(String[] params) {
		try {
			Parameters.setParameters("parameters.json");
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			MapWithJungle firstMap = new MapWithJungle(Parameters.MAP_WIDTH, Parameters.MAP_HEIGHT, (float)0.2, 10, 10000);
			MapWithJungle secondMap =  new MapWithJungle(Parameters.MAP_WIDTH, Parameters.MAP_HEIGHT, (float)0.2, 10, 10000);
			Frame frame = new Frame(firstMap, secondMap, status, 1200, 800);
			frame.updateFrame();


		while(true)
		{
			if(status.isFirstSceneOn)
			{

				firstMap.nextDay();
			}
			if(status.isSecondSceneOn) secondMap.nextDay();
			frame.updateFrame();
		}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

	public static void saveToFile(String filePath, String content) {
		File file1 = new File(filePath);

		try {

			if (!file1.exists()) {

				file1.createNewFile();
			}


			File[] files = file1.listFiles();


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


