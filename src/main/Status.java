package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


enum SceneNumber
{
    FIRST_SCENE, SECOND_SCENE;
}
public class Status{

   public  boolean isFirstSceneOn=true;
   public boolean isSecondSceneOn=true;
   public SceneNumber whichSceneToDisplay=SceneNumber.FIRST_SCENE;
}
