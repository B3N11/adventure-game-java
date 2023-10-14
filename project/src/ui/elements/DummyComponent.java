package ui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;

public class DummyComponent extends Component implements IGridPositionable{

    private GridPosition gridPosition;

    public DummyComponent(int x, int y, GridBagConstraints gbc){
        gridPosition = new GridPosition(gbc.gridx, gbc.gridy);
        initComponent(x, y);      
    }

    private void initComponent(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setBounds(0, 0, x, y);  
        //setOpaque(false);     //Use JPanel instead of Component, if transparency is needed
    }

    @Override
    public GridPosition getGridPosition() {
        return gridPosition;
    }
}