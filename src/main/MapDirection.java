package main;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST;

    public String toString()
    {
        if(this==NORTH)return new String("polnoc");
        else if(this==SOUTH)return new String("poludnie");
        else if(this==WEST)return new String("zachod");
        else if(this==EAST)return new String("wschod");
        return "";
    }

    public MapDirection next()
    {
        if(this==NORTH)return NORTHEAST;
        else if(this==NORTHEAST)return EAST;
        else if(this==EAST)return SOUTHEAST;
        else if(this==SOUTHEAST)return SOUTH;
        else if(this==SOUTH)return SOUTHWEST;
        else if(this==SOUTHWEST)return WEST;
        else if(this==WEST)return NORTHWEST;
        else if(this==NORTHWEST)return NORTH;
        return null;
    }

    public MapDirection previous()
    {
        if(this==NORTH)return NORTHWEST;
        else if(this==NORTHWEST)return WEST;
        else if(this==WEST)return SOUTHWEST;
        else if(this==SOUTHWEST)return SOUTH;
        else if(this==SOUTH)return SOUTHEAST;
        else if(this==SOUTHEAST)return EAST;
        else if(this==EAST)return NORTHEAST;
        else if(this==NORTHEAST)return NORTH;
        return null;
    }

    public Vector2d toUnitVector()
    {
        switch(this)
        {
            case EAST: return new Vector2d(1,0);
            case WEST: return new Vector2d(-1, 0);
            case NORTH: return new Vector2d(0, 1);
            case SOUTH: return new Vector2d(0, -1);
            case NORTHEAST: return new Vector2d(1,1);
            case SOUTHEAST: return new Vector2d(1,-1);
            case NORTHWEST: return new Vector2d(-1,1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            default: return null;
        }
    }

}
