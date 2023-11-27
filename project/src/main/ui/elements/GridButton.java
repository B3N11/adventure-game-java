package main.ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import main.exception.general.ArgumentNullException;
import main.exception.general.InvalidArgumentException;
import main.uilogic.GridPosition;
import main.uilogic.IGridPositionable;

/**
 * This class represents a grid button in the game. It is placed on the top of an InteractiveGrid panel.
 * It extends the JButton class and implements the IGridPositionable interface.
 * 
 * The class contains the following field:
 * - gridPosition: The grid position of the grid button.
 */
public class GridButton extends JButton implements IGridPositionable{
    
    private GridPosition gridPosition;

    /**
     * Constructor for the GridButton class.
     * Initializes the grid button with the specified width, height, border color, grid position, and handler.
     * @param width The width of the grid button.
     * @param height The height of the grid button.
     * @param borderColor The border color of the grid button.
     * @param position The grid position of the grid button.
     * @param handler The handler of the grid button.
     * @throws ArgumentNullException if the border color, grid position, or handler is null.
     */
    public GridButton(int width, int height, Color borderColor, GridPosition position, ActionListener handler){
        initVisuals(width, height, borderColor);
        initData(position, handler);
    }

    /**
     * Constructor for the GridButton class.
     * Initializes the grid button with the specified width, height, x, y, border color, and handler.
     * @param width The width of the grid button.
     * @param height The height of the grid button.
     * @param x The x of the grid button.
     * @param y The y of the grid button.
     * @param borderColor The border color of the grid button.
     * @param handler The handler of the grid button.
     * @throws InvalidArgumentException if the x or y is invalid.
     * @throws ArgumentNullException if the border color or handler is null.
     */
    public GridButton(int width, int height, int x, int y, Color borderColor, ActionListener handler) throws InvalidArgumentException{
        initVisuals(width, height, borderColor);
        initData(new GridPosition(x, y), handler);
    }

    /**
     * Initializes the visuals of the grid button.
     * Sets the preferred size, bounds, opacity, content area filled status, border painted status, rollover enabled status, and border of the grid button.
     * @param width The width of the grid button.
     * @param height The height of the grid button.
     * @param borderColor The border color of the grid button.
     */
    private void initVisuals(int width, int height, Color borderColor){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        setRolloverEnabled(false);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    /**
     * Initializes the data of the grid button.
     * Sets the grid position and action listener of the grid button.
     * @param position The grid position of the grid button.
     * @param listener The action listener of the grid button.
     */
    private void initData(GridPosition position, ActionListener listener){
        gridPosition = position;
        addActionListener(listener);
    }

    /**
     * Highlights the grid button.
     * Sets the opacity, content area filled status, and background color of the grid button.
     * @param highlight A boolean that determines whether to highlight the grid button.
     */
    public void hightlightButton(boolean highlight){
        setOpaque(highlight);
        setContentAreaFilled(highlight);

        if(highlight){
            setBackground(new Color(198, 204, 201, 100));
        }
        else{
            setBackground(new Color(255, 255, 255, 0));
        }
    }

    @Override
    public GridPosition getGridPosition(){
        return gridPosition;
    }

    @Override
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException{
        if(newPosition == null)
            throw new ArgumentNullException();
            
        gridPosition = newPosition;
        return gridPosition;
    }
}
