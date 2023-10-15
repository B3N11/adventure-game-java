package ui.elements;

import java.awt.GridBagConstraints;
import java.io.IOException;

import exception.general.ArgumentNullException;
import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;

public class GridEntityComponent extends ImageComponent implements IGridPositionable{

    private String id;
    private GridPosition gridPosition;

    public GridEntityComponent(String id, int width, int height, GridBagConstraints gbc) throws ArgumentNullException{
        super(width, height);

        if(id == null || gbc == null)
            throw new ArgumentNullException();
            
        gridPosition = new GridPosition(gbc);
        this.id = id;
    }

    public String getID(){ return id; }  

    @Override
    public GridEntityComponent setImage(String filePath) throws ArgumentNullException, IOException{
        super.setImage(filePath);
        return this;
    }

    public GridBagConstraints getGridPositionAsGBC(){
        var result = new GridBagConstraints();
        result.gridx = gridPosition.getX();
        result.gridy = gridPosition.getY();

        return result;
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