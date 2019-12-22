package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImagePanel extends JPanel
{

    private Image animalImage;
    private Image plantImage;
    private MapWithJungle map;
    private int cellSizeX;
    private int cellSizeY;
    public ImagePanel(MapWithJungle map, int cellSizeX, int cellSizeY) {
        super();
        this.map=map;
        this.cellSizeX=cellSizeX;
        this.cellSizeY=cellSizeY;
        BufferedImage image=null;
        File imageFile = new File("grass.jpg");
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Cannot read image file");
            e.printStackTrace();
        }
        plantImage = image.getScaledInstance(cellSizeX,cellSizeY, Image.SCALE_SMOOTH);

       imageFile = new File("animal.jpg");
       try
       {
           image=ImageIO.read(imageFile);
       } catch(IOException e)
       {
           System.err.println("Cannot read image file");
           e.printStackTrace();
       }
        animalImage = image.getScaledInstance(cellSizeX,cellSizeY, Image.SCALE_SMOOTH);
       // Dimension dimension = new Dimension(cellSizeX*Parameters.MAP_WIDTH, cellSizeY*Parameters.MAP_HEIGHT);
      //  setPreferredSize(dimension);


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<DrawableElement> drawables = map.getDrawables();

        for(DrawableElement element: drawables)
        {
            if(element.type==DrawableType.ANIMAL) g2d.drawImage(animalImage, (element.position.x-1)*cellSizeX, (Parameters.MAP_HEIGHT-element.position.y)*cellSizeY, this);
            else if (element.type==DrawableType.PLANT) g2d.drawImage(plantImage, (element.position.x-1)*cellSizeX, (Parameters.MAP_HEIGHT-element.position.y)*cellSizeY, this);
        }
    }
}