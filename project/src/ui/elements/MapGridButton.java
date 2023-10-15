package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;
import uilogic.GridButtonHandler;

public class MapGridButton extends JButton implements IGridPositionable{
    
    private GridPosition gridPosition;

    public MapGridButton(int width, int height, Color borderColor, GridBagConstraints gbc, GridButtonHandler handler){
        initVisuals(width, height, borderColor);
        initData(gbc.gridx, gbc.gridy, handler);
    }

    public MapGridButton(int width, int height, int x, int y, Color borderColor, GridButtonHandler handler){
        initVisuals(width, height, borderColor);
        initData(x, y, handler);
    }

    private void initVisuals(int width, int height, Color borderColor){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    private void initData(int x, int y, ActionListener listener){
        gridPosition = new GridPosition(x, y);
        addActionListener(listener);
    }
    //#endregion

    public GridPosition getGridPosition(){
        return gridPosition;
    }
}
