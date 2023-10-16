package ui.data;

import java.awt.Dimension;

public class GridDimension extends Dimension{
    
    public GridDimension(int x, int y){
        super(x,y);
    }

    public int getHorizontal(){
        return (int)super.getWidth();
    }

    public int getVertical(){
        return (int)super.getHeight();
    }
}