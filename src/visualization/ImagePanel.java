package visualization;

import main.Parameters;
import world.Animal;
import world.IMapElement;
import world.MapWithJungle;
import world.Plant;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImagePanel extends JPanel
{

    private Image scaredBearImage;
    private Image normalBearImage;
    private Image turboBearImage;
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

       imageFile = new File("bear1.png");
       try
       {
           image=ImageIO.read(imageFile);
       } catch(IOException e)
       {
           System.err.println("Cannot read image file");
           e.printStackTrace();
       }
        normalBearImage = image.getScaledInstance(cellSizeX,cellSizeY, Image.SCALE_SMOOTH);

        imageFile = new File("bear2.png");
        try
        {
            image=ImageIO.read(imageFile);
        } catch(IOException e)
        {
            System.err.println("Cannot read image file");
            e.printStackTrace();
        }
        turboBearImage = image.getScaledInstance(cellSizeX,cellSizeY, Image.SCALE_SMOOTH);

        imageFile = new File("bear3.png");
        try
        {
            image=ImageIO.read(imageFile);
        } catch(IOException e)
        {
            System.err.println("Cannot read image file");
            e.printStackTrace();
        }
        scaredBearImage = image.getScaledInstance(cellSizeX,cellSizeY, Image.SCALE_SMOOTH);



    }


    private Image getImage(IMapElement element)
    {
        if(element instanceof Animal)
        {
            Animal animal = (Animal)element;
            if(animal.getEnergy()<0.2* Parameters.START_ENERGY)return scaredBearImage;
            else if(animal.getEnergy()<0.8*Parameters.START_ENERGY)return normalBearImage;
            else return turboBearImage;
        }
        else if (element instanceof Plant)return plantImage;
        return null;
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<IMapElement> drawables = map.getDrawables();

        for(IMapElement element: drawables)
        {
            g2d.drawImage(getImage(element), (element.getPosition().x-1)*cellSizeX, (Parameters.MAP_HEIGHT-element.getPosition().y)*cellSizeY, this);
        }
    }
}