package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import ui.data.GridPosition;
import uilogic.IGridPositionable;

public class GridButton extends JButton implements IGridPositionable{
    
    private GridPosition gridPosition;

    public GridButton(int width, int height, Color borderColor, GridPosition position, ActionListener handler){
        initVisuals(width, height, borderColor);
        initData(position, handler);
    }

    public GridButton(int width, int height, int x, int y, Color borderColor, ActionListener handler) throws InvalidArgumentException{
        initVisuals(width, height, borderColor);
        initData(new GridPosition(x, y), handler);
    }

    private void initVisuals(int width, int height, Color borderColor){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        setRolloverEnabled(false);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    private void initData(GridPosition position, ActionListener listener){
        gridPosition = position;
        addActionListener(listener);
    }

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
