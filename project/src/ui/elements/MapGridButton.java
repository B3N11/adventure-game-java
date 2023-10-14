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

    public MapGridButton(int width, int height, GridBagConstraints gbc, GridButtonHandler handler){
        initVisuals(width, height);
        initData(gbc.gridx, gbc.gridy, handler);
    }

    public MapGridButton(int width, int height, int x, int y, GridButtonHandler handler){
        initVisuals(width, height);
        initData(x, y, handler);
    }

    private void initVisuals(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        //setBorderPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.red));
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
