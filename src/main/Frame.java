package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
class AnimalInfo extends JPopupMenu {
    public AnimalInfo(Animal animal) {
        JLabel anItem;
        anItem = new JLabel("ID: "+animal.getID());
        add(anItem);
        anItem = new JLabel("Energy: "+animal.getEnergy());
        add(anItem);
        anItem = new JLabel("Genome: "+animal.getGenome());
        add(anItem);
        anItem = new JLabel("Number of children: ");
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
     private int cellSizeX;
     private int cellSizeY;


     private Vector2d convertPixelPositionToMapPosition(Vector2d pixelPosition)
     {
            return new Vector2d((int)Math.floor(pixelPosition.x/cellSizeX)+1, Parameters.MAP_HEIGHT-(int)Math.floor(pixelPosition.y/cellSizeY));
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
        layout= new FlowLayout();
        setLayout(layout);
        setResizable(false);
        pauseButton = new Button("Pause");
        pauseButton.setPreferredSize(new Dimension(200, 400));
        pauseButton.addActionListener(this);


        changeSceneButton = new Button("Go to second scene");
        changeSceneButton.setPreferredSize(new Dimension(200, 400));
        changeSceneButton.addActionListener(this);


        add(pauseButton);
        add(changeSceneButton);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                Vector2d mousePosition = new Vector2d(e.getX(), e.getY());
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
        }
        else if(status.whichSceneToDisplay==SceneNumber.SECOND_SCENE)
        {
            imagePanel = new ImagePanel(secondMap, cellSizeX, cellSizeY);
        }

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







