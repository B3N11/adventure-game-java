package ui.elements;

import java.io.IOException;

import exception.general.ArgumentNullException;
import ui.data.GridPosition;
import ui.interfaces.IGridPositionable;

public class GridEntityComponent extends ImageComponent implements IGridPositionable{

    private String id;
    private GridPosition gridPosition;

    public GridEntityComponent(String id, int width, int height, GridPosition position) throws ArgumentNullException{
        super(width, height);

        if(id == null || position == null)
            throw new ArgumentNullException();
            
        gridPosition = position;
        this.id = id;
    }

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