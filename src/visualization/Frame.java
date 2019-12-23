package visualization;

import main.*;
import world.Animal;
import world.MapWithJungle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AnimalInfo extends JPopupMenu {
    public AnimalInfo(Animal animal) {
        JLabel anItem;
        anItem = new JLabel("ID: "+animal.getID());
        add(anItem);
        anItem = new JLabel("Energy: "+animal.getEnergy());
        add(anItem);
        anItem = new JLabel("Genome: "+animal.getGenome());
        add(anItem);
        anItem = new JLabel("Number of children: "+animal.getNumberOfChildren());
        add(anItem);
    }
}


public class Frame extends JFrame  implements ActionListener {


     private static String pauseButtonText="Pause";
     private static String continueButtonText="Continue";
     private static String firstSceneText="Go to first scene";
     private static String secondSceneText="Go to second scene";
     private MapWithJungle firstMap;
     private MapWithJungle secondMap;
     private JPanel oldPanel;
     private Status status;
     private int sizeX;
     private int sizeY;
     private int cellSizeX;
     private int cellSizeY;

     //Side panel contents
     private Panel sidePanel;
     private JButton pauseButton;
     private JButton changeSceneButton;
     private JButton saveStatisticsButton;
     private JButton quitButton;
     private JLabel statisticsLabel;

     private static String saveStaticticsToString(MapWithJungle map, boolean forLabel)
     {
         StringBuilder builder=new StringBuilder();
         String splittingCharacter;
         if(forLabel)splittingCharacter="<br/>";
         else splittingCharacter="\n";

         builder.append("Day: "+map.whichDay()+splittingCharacter);
         builder.append("Number of alive animals:"+map.howManyAnimals()+splittingCharacter);
         builder.append("Number of plants: "+map.howManyPlants()+splittingCharacter);
         builder.append("Most popular genome: "+map.getMostPopularGenome()+splittingCharacter);
         builder.append("Avarage energy: " + map.getAverageEnergy()+splittingCharacter);
         builder.append("Avarage lifespan: "+map.getAverageLifespan()+splittingCharacter);
         builder.append("Avarage number of children: "+map.getAverageNumberOfChildren());

        if(forLabel) return "<html>"+builder.toString()+"</html>";
        return builder.toString();

     }

     public Vector2d convertPixelPositionToMapPosition(Vector2d pixelPosition)
     {
            return new Vector2d((int)Math.ceil(pixelPosition.x/cellSizeX)+1, Parameters.MAP_HEIGHT-(int)Math.ceil(pixelPosition.y/cellSizeY));
     }

     public Vector2d convertPostionOnMapToPixelPostion(Vector2d positionOnMap)
     {
         return new Vector2d((positionOnMap.x-1)*cellSizeX, (Parameters.MAP_HEIGHT-positionOnMap.y)*cellSizeY);
     }

    public Frame(MapWithJungle firstMap, MapWithJungle secondMap, Status status, int sizeX, int sizeY){

        super("Visualization");
        this.firstMap=firstMap;
        this.secondMap=secondMap;
        this.oldPanel=null;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.status=status;

        this.cellSizeX= (int)Math.ceil(sizeX*(1.0/Parameters.MAP_WIDTH));
        this.cellSizeY = (int) Math.ceil(sizeY*(1.0/Parameters.MAP_HEIGHT));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout( new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        int sidePanelWidth =(int) Math.floor(Parameters.WINDOW_WIDTH/6.0);
        int sidePanelHeight =(int) Math.floor(Parameters.WINDOW_HEIGHT/5.0);

        pauseButton = new JButton(pauseButtonText);
        pauseButton.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));
        pauseButton.addActionListener(this);


        changeSceneButton = new JButton(secondSceneText);
        changeSceneButton.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));
        changeSceneButton.addActionListener(this);

        saveStatisticsButton=new JButton("Save statistics");
        saveStatisticsButton.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));
        saveStatisticsButton.addActionListener(this);


        quitButton=new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));
        quitButton.addActionListener(this);

        statisticsLabel=new JLabel();
        statisticsLabel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));

        sidePanel=new Panel();
        sidePanel.setLayout(new GridLayout(5, 1));
        sidePanel.add(pauseButton);
        sidePanel.add(changeSceneButton);
        sidePanel.add(saveStatisticsButton);
        sidePanel.add(quitButton);
        sidePanel.add(statisticsLabel);

        add(sidePanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                Vector2d mousePosition = new Vector2d(e.getX()-2, e.getY()-2);//we have to move mouse position by vector (2, 2) because that is the size of border
                if(mousePosition.isInside(new Vector2d(0,0), new Vector2d(sizeX, sizeY))) {
                    if (status.whichSceneToDisplay == SceneNumber.FIRST_SCENE && !status.isFirstSceneOn)
                    {
                        if(firstMap.isOccupiedByAnimal(convertPixelPositionToMapPosition(mousePosition)))
                        {
                            AnimalInfo animalInfo = new AnimalInfo(firstMap.getAnimalAtPosition(convertPixelPositionToMapPosition(mousePosition)));
                            animalInfo.show(e.getComponent(), e.getX(), e.getY());
                        }

                    } else if (status.whichSceneToDisplay == SceneNumber.SECOND_SCENE && !status.isSecondSceneOn)
                    {
                        if(secondMap.isOccupiedByAnimal(convertPixelPositionToMapPosition(mousePosition)))
                        {
                            AnimalInfo animalInfo = new AnimalInfo(secondMap.getAnimalAtPosition(convertPixelPositionToMapPosition(mousePosition)));
                            animalInfo.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
                //updateFrame();
            }

        });

        pack();
        setVisible(true);
    }


    public void updateFrame()
    {

        ImagePanel imagePanel=null;
        if(status.whichSceneToDisplay==SceneNumber.FIRST_SCENE)
        {
            imagePanel = new ImagePanel(firstMap, this, cellSizeX, cellSizeY);
            sidePanel.remove(statisticsLabel);
            statisticsLabel=new JLabel(saveStaticticsToString(firstMap, true));


        }
        else if(status.whichSceneToDisplay==SceneNumber.SECOND_SCENE)
        {
            imagePanel = new ImagePanel(secondMap, this, cellSizeX, cellSizeY);
            sidePanel.remove(statisticsLabel);
            statisticsLabel=new JLabel(saveStaticticsToString(secondMap, true));
        }
        statisticsLabel.setPreferredSize(new Dimension(400, 100));
        sidePanel.add(statisticsLabel);
        imagePanel.setPreferredSize(new Dimension(sizeX, sizeY));
        if (oldPanel != null) remove(oldPanel);
        oldPanel = imagePanel;
        add(imagePanel,0);
        pack();

        revalidate();
        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pauseButton)
        {

            if(pauseButton.getText().equals(pauseButtonText))
            {

                pauseButton.setText(continueButtonText);
            }
            else if(pauseButton.getText().equals(continueButtonText)) pauseButton.setText(pauseButtonText);

            if(status.whichSceneToDisplay==SceneNumber.FIRST_SCENE)status.isFirstSceneOn=!status.isFirstSceneOn;
            else status.isSecondSceneOn=!status.isSecondSceneOn;

        }
        else if(e.getSource()==changeSceneButton)
        {
            if(changeSceneButton.getText().equals(secondSceneText))
            {
                status.whichSceneToDisplay=SceneNumber.SECOND_SCENE;
                changeSceneButton.setText(firstSceneText);
                if(status.isSecondSceneOn) pauseButton.setText(pauseButtonText);
                else pauseButton.setText(continueButtonText);

            }
            else if(changeSceneButton.getText().equals(firstSceneText))
            {
                status.whichSceneToDisplay=SceneNumber.FIRST_SCENE;
                changeSceneButton.setText(secondSceneText);
                if(status.isFirstSceneOn) pauseButton.setText(pauseButtonText);
                else pauseButton.setText(continueButtonText);
            }
        }
        else if(e.getSource()==saveStatisticsButton)
        {
            MapWithJungle map;
            if(status.whichSceneToDisplay==SceneNumber.FIRST_SCENE)map=firstMap;
            else map=secondMap;
            Main.saveToFile(Parameters.STATISTICS_FILE_PATH,saveStaticticsToString(map, false));
        }
        else if(e.getSource()==quitButton)
        {
            System.exit(0);
        }
        revalidate();
        repaint();
    }
}






