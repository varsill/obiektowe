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

     private MapWithJungle firstMap;
     private MapWithJungle secondMap;
     private JPanel oldPanel;
     private Status status;
     private int sizeX;
     private int sizeY;
     private FlowLayout layout;
     private Button pauseButton;
     private Button changeSceneButton;
     private Button saveStatisticsButton;
     private Button quitButton;
     private int cellSizeX;
     private int cellSizeY;
     private Panel sidePanel;
     private JLabel statisticsLabel;


     private Vector2d convertPixelPositionToMapPosition(Vector2d pixelPosition)
     {
            return new Vector2d((int)Math.ceil(pixelPosition.x/cellSizeX)+1, Parameters.MAP_HEIGHT-(int)Math.ceil(pixelPosition.y/cellSizeY));
     }

     private Vector2d convertPostionOnMapToPixelPostion(Vector2d positionOnMap)
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
        //this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout( new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        pauseButton = new Button("Pause");
        pauseButton.setPreferredSize(new Dimension(200, 150));
        pauseButton.addActionListener(this);


        changeSceneButton = new Button("Go to second scene");
        changeSceneButton.setPreferredSize(new Dimension(200, 150));
        changeSceneButton.addActionListener(this);

        saveStatisticsButton=new Button("Save statistics");
        saveStatisticsButton.setPreferredSize(new Dimension(200, 150));
        saveStatisticsButton.addActionListener(this);


        quitButton=new Button("Quit");
        quitButton.setPreferredSize(new Dimension(200, 150));
        quitButton.addActionListener(this);

        statisticsLabel=new JLabel();
        statisticsLabel.setPreferredSize(new Dimension(200, 150));

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
             //   updateFrame();
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
            imagePanel = new ImagePanel(firstMap, cellSizeX, cellSizeY);
            sidePanel.remove(statisticsLabel);
            statisticsLabel=new JLabel("<html>"+
                    "Day: "+firstMap.whichDay()+"<br/>"
                    +"Number of alive animals:"+firstMap.howManyAnimals()+"<br/>"
                            +"Number of plants: "+firstMap.howManyPlants()+"<br/>"
                    +"Most popular genome: "+firstMap.getMostPopularGenome()+"<br/>"
                            +"Avarage energy: " + firstMap.getAvarageEnergy()+"<br/>"
                            +"Avarage lifespan: "+firstMap.getAvarageLifespan()+"<br/>"
                            +"Avarage number of children: "+firstMap.getAvarageNumberOfChildren()
                    +"</html>"

            );

        }
        else if(status.whichSceneToDisplay==SceneNumber.SECOND_SCENE)
        {
            imagePanel = new ImagePanel(secondMap, cellSizeX, cellSizeY);
            sidePanel.remove(statisticsLabel);
            statisticsLabel=new JLabel("<html>"+
                    "Day: "+secondMap.whichDay()+"<br/>"
                   + "Number of alive animals:"+secondMap.howManyAnimals()+"<br/>"
                            +"Number of plants: "+secondMap.howManyPlants()+"<br/>"
                    +"Most popular genome: "+secondMap.getMostPopularGenome()+"<br/>"
                    +"Avarage energy: " + secondMap.getAvarageEnergy()+"<br/>"
                    +"Avarage lifespan: "+secondMap.getAvarageLifespan()+"<br/>"
                    +"Avarage number of children: "+secondMap.getAvarageNumberOfChildren()
                    +"</html>"


            );

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

            if(pauseButton.getText().equals("Pause"))
            {

                pauseButton.setText("Continue");
            }
            else if(pauseButton.getText().equals("Continue")) pauseButton.setText("Pause");

            if(status.whichSceneToDisplay==SceneNumber.FIRST_SCENE)status.isFirstSceneOn=!status.isFirstSceneOn;
            else status.isSecondSceneOn=!status.isSecondSceneOn;

        }
        else if(e.getSource()==changeSceneButton)
        {
            if(changeSceneButton.getText().equals("Go to second scene"))
            {
                status.whichSceneToDisplay=SceneNumber.SECOND_SCENE;
                changeSceneButton.setText("Go to first scene");
                if(status.isSecondSceneOn) pauseButton.setText("Pause");
                else pauseButton.setText("Continue");

            }
            else if(changeSceneButton.getText().equals("Go to first scene"))
            {
                status.whichSceneToDisplay=SceneNumber.FIRST_SCENE;
                changeSceneButton.setText("Go to second scene");
                if(status.isFirstSceneOn) pauseButton.setText("Pause");
                else pauseButton.setText("Continue");
            }
        }
        else if(e.getSource()==saveStatisticsButton)
        {
            MapWithJungle map;
            if(status.whichSceneToDisplay==SceneNumber.FIRST_SCENE)map=firstMap;
            else map=secondMap;
            Main.saveToFile(Parameters.STATISTICS_FILE_PATH,
                    "Day: "+map.whichDay()+"\n"
                            +"Number of alive animals:"+map.howManyAnimals()+"\n"
                            +"Number of plants: "+map.howManyPlants()+"\n"
                            +"Most popular genome: "+map.getMostPopularGenome()+"\n"
                            +"Avarage energy: " + map.getAvarageEnergy()+"\n"
                            +"Avarage lifespan: "+map.getAvarageLifespan()+"\n"
                            +"Avarage number of children: "+map.getAvarageNumberOfChildren()
                    );
        }
        else if(e.getSource()==quitButton)
        {
            System.exit(0);
        }
        revalidate();
        repaint();
    }
}

 class Button extends JButton
{


    public Button(String text)
    {
        super(text);

    }


}







