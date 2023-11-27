package main.uilogic;

import java.awt.GridBagConstraints;
import java.io.Serializable;

import main.exception.general.ArgumentNullException;
import main.exception.general.InvalidArgumentException;

/**
 * This class represents a position on a grid.
 * It contains x and y coordinates and implements Serializable for object serialization.
 * 
 * The class contains the following fields:
 * - x: The x-coordinate of the grid position.
 * - y: The y-coordinate of the grid position.
 */
public class GridPosition implements Serializable{
    
    private int x;
    private int y;
    /**
     * Default constructor for the GridPosition class.
     * Initializes the grid position with x and y coordinates set to 0.
     * @throws InvalidArgumentException if the x-coordinate or y-coordinate is less than 0.
     */
    public GridPosition() throws InvalidArgumentException{
        this(0,0);
    }

    /**
     * Constructor for the GridPosition class.
     * Initializes the grid position with specified x and y coordinates.
     * @param x The x-coordinate of the grid position.
     * @param y The y-coordinate of the grid position.
     * @throws InvalidArgumentException if the x-coordinate or y-coordinate is less than 0.
     */
    public GridPosition(int x, int y) throws InvalidArgumentException{
        setPosition(x, y);
    }
    
    /**
     * Constructor for the GridPosition class.
     * Initializes the grid position with the x and y coordinates of a GridBagConstraints object.
     * @param gbc The GridBagConstraints object.
     * @throws InvalidArgumentException if the x-coordinate or y-coordinate of the GridBagConstraints object is less than 0.
     */
    public GridPosition(GridBagConstraints gbc) throws InvalidArgumentException{
        this(gbc.gridx, gbc.gridy);
    }

    /**
     * Gets the x-coordinate of the grid position.
     * @return The x-coordinate of the grid position.
     */
    public int getX(){ return x; }
    
    /**
     * Gets the y-coordinate of the grid position.
     * @return The y-coordinate of the grid position.
     */
    public int getY(){ return y; }

    /**
     * Gets the grid position as a GridBagConstraints object.
     * @return The GridBagConstraints object representing the grid position.
     */
    public GridBagConstraints getAsGridBagConstraints(){
        var result = new GridBagConstraints();
        result.gridx = x;
        result.gridy = y;
        return result;
    }

    /**
     * Sets the x-coordinate and y-coordinate of the grid position.
     * @param x The x-coordinate to set.
     * @param y The y-coordinate to set.
     * @return The GridPosition object with the set coordinates.
     * @throws InvalidArgumentException if the x-coordinate or y-coordinate is less than 0.
     */
    public GridPosition setPosition(int x, int y) throws InvalidArgumentException{
        if(x < 0 || y < 0)
            throw new InvalidArgumentException();
        
        this.x = x;
        this.y = y;

        return this;
    }

    /**
     * Calculates the absolute distance between two grid positions.
     * @param src The source grid position.
     * @param dst The destination grid position.
     * @return The absolute distance between the source and destination grid positions.
     * @throws ArgumentNullException if the source or destination grid position is null.
     */
    public static double calculateAbsoluteDistance(GridPosition src, GridPosition dst) throws ArgumentNullException{
        if(src == null || dst == null)
            throw new ArgumentNullException();

        double xDistance = Math.abs(src.getX() - dst.getX());
        double yDistance = Math.abs(src.getY() - dst.getY());
        double sum = Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
        double result = Math.sqrt(sum);        

        //result = Double.parseDouble(String.format("%.2f", result));

        return result;
    }

    /**
     * Checks if the grid position is equal to another grid position.
     * @param cmp The grid position to compare with.
     * @return A boolean indicating whether the grid position is equal to the other grid position.
     */
    public boolean equals(GridPosition cmp){
        return (x == cmp.x) && (y == cmp.y);
    }
}
