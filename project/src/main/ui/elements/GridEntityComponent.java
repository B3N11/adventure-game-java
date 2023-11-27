package main.ui.elements;

import java.io.IOException;

import main.exception.general.ArgumentNullException;
import main.uilogic.GridPosition;
import main.uilogic.IGridPositionable;

/**
 * This class represents a grid entity component in the game. It is used to display and store the position of an entity on the grid.
 * It extends the ImageComponent class and implements the IGridPositionable interface.
 * 
 * The class contains the following fields:
 * - id: The id of the grid entity component.
 * - gridPosition: The grid position of the grid entity component.
 */
public class GridEntityComponent extends ImageComponent implements IGridPositionable{

    private String id;
    private GridPosition gridPosition;

    /**
     * Constructor for the GridEntityComponent class.
     * Initializes the grid entity component with the specified id, width, height, and grid position.
     * @param id The id of the grid entity component.
     * @param width The width of the grid entity component.
     * @param height The height of the grid entity component.
     * @param position The grid position of the grid entity component.
     * @throws ArgumentNullException if the id or grid position is null.
     */
    public GridEntityComponent(String id, int width, int height, GridPosition position) throws ArgumentNullException{
        super(width, height);

        if(id == null || position == null)
            throw new ArgumentNullException();
            
        gridPosition = position;
        this.id = id;
    }

    /**
     * Gets the id of the grid entity component.
     * @return The id of the grid entity component.
     */
    public String getID(){ return id; }  

    @Override
    public GridEntityComponent setImage(String filePath) throws ArgumentNullException, IOException{
        super.setImage(filePath);
        return this;
    }

    @Override
    public GridPosition getGridPosition() {
        return gridPosition;
    } 

    @Override
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException {
        if(newPosition == null)
            throw new ArgumentNullException();
        
        gridPosition = newPosition;
        return gridPosition;
    }
}