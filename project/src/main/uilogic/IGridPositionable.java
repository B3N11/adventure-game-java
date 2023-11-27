package main.uilogic;

import main.exception.general.ArgumentNullException;

/**
 * This interface represents an object that can be positioned on a grid.
 * It contains methods for getting and setting the grid position of the object.
 */
public interface IGridPositionable {
    /**
     * Gets the grid position of the object.
     * @return The grid position of the object.
     */
    public GridPosition getGridPosition();
    
    /**
     * Sets the grid position of the object.
     * @param newPosition The new grid position of the object.
     * @return The new grid position of the object.
     * @throws ArgumentNullException if the new grid position is null.
     */
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException;
}
