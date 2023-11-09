package ui.elements;

import java.awt.Dimension;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import ui.data.GridPosition;
import ui.interfaces.IGridPositionable;

public class DummyComponent extends JPanel implements IGridPositionable{

    private GridPosition gridPosition;

    public DummyComponent(int x, int y, GridPosition position) throws ArgumentNullException{
        setGridPosition(position);
        initComponent(x, y);      
    }

    private void initComponent(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setBounds(0, 0, x, y);  
        setOpaque(false);     //Use JPanel instead of Component, if transparency is needed
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