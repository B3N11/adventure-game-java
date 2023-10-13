package ui.elements;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;

import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;

public class DummyComponent extends JPanel implements IGridPositionable{

    private GridPosition gridPosition;

    public DummyComponent(int x, int y, GridBagConstraints gbc){
        gridPosition = new GridPosition(gbc.gridx, gbc.gridy);
        setSize(x, y);
        setBounds(0, 0, x, y);
    }

    @Override
    public GridPosition getGridPosition() {
        return gridPosition;
    }
}