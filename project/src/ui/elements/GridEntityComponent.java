package ui.elements;

import java.awt.GridBagConstraints;
import java.io.IOException;

import exception.general.ArgumentNullException;
import ui.data.GridDimension;
import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;

public class GridEntityComponent extends GridImageComponent implements IGridPositionable{

    private String id;
    private GridPosition gridPosition;

    public GridEntityComponent(String id, int width, int height, GridBagConstraints gbc) throws ArgumentNullException{
        super(width, height);

        if(id == null || gbc == null)
            throw new ArgumentNullException();
            
        preferredSize = new GridDimension(width, height);
        gridPosition = new GridPosition(gbc);

        setPreferredSize(preferredSize);
        setBounds(0, 0, width, height);
    }

    public String getID(){ return id; }

    public GridPosition getGridPosition() {
        return gridPosition;
    }   

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
}