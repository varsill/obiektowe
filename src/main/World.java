package main;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class World
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
			if(status.isFirstSceneOn) firstMap.nextDay();
			if(status.isSecondSceneOn) secondMap.nextDay();
			frame.updateFrame();
		}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

}


