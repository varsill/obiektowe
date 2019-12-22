package main;

import java.util.ArrayList;

public class OptionsParser {
    public static MoveDirection[] parse(String[] dirs) throws IllegalArgumentException
    {
        ArrayList<MoveDirection> list = new ArrayList<MoveDirection>();


        for(String dir:dirs)
        {
            if(dir.equals("f")|| dir.equals("forward"))
            {
                list.add(MoveDirection.FORWARD);
            }
            else if(dir.equals("b")|| dir.equals("backward"))
            {
                list.add(MoveDirection.BACKWARD);
            }
            else if(dir.equals("r")|| dir.equals("right"))
            {
                list.add(MoveDirection.RIGHT);
            }
            else if(dir.equals("l")|| dir.equals("left"))
            {
                list.add(MoveDirection.LEFT);
            }
            else
            {
                throw new IllegalArgumentException(dir+" is not legal move specification.");
            }
        }

        return (MoveDirection[])list.toArray(new MoveDirection[list.size()]);
    }
}
