package main.ui.data;

import java.awt.Dimension;

/**
 * This class represents a dimension in a grid.
 * It extends the Dimension class and contains methods to get the horizontal and vertical dimensions.
 */
public class GridDimension extends Dimension{
    
    /**
     * Constructor for the GridDimension class.
     * Initializes the dimension with the specified horizontal and vertical dimensions.
     * @param x The horizontal dimension.
     * @param y The vertical dimension.
     */
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