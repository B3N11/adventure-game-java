package main.ui.elements;

import java.awt.Dimension;
import javax.swing.JPanel;

import main.exception.general.ArgumentNullException;
import main.uilogic.GridPosition;
import main.uilogic.IGridPositionable;

/**
 * This class represents a dummy component in the game. It is used to fill empty grid positions.
 * It extends the JPanel class and implements the IGridPositionable interface.
 * 
 * The class contains the following field:
 * - gridPosition: The grid position of the dummy component.
 */
public class DummyComponent extends JPanel implements IGridPositionable{

    private GridPosition gridPosition;

    /**
     * Constructor for the DummyComponent class.
     * Initializes the dummy component with the specified x, y, and grid position.
     * @param x The x of the dummy component.
     * @param y The y of the dummy component.
     * @param position The grid position of the dummy component.
     * @throws ArgumentNullException if the grid position is null.
     */
    public DummyComponent(int x, int y, GridPosition position) throws ArgumentNullException{
        setGridPosition(position);
        initComponent(x, y);      
    }

    /**
     * Initializes the dummy component.
     * Sets the preferred size, bounds, and opacity of the dummy component.
     * @param x The x of the dummy component.
     * @param y The y of the dummy component.
     */
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