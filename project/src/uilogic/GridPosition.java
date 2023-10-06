package uilogic;

public class GridPosition {
    private int x;
    private int y;

    public GridPosition(){
        this(0,0);
    }

    public GridPosition(int horizontal, int vertical){
        x = horizontal;
        y = vertical;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}