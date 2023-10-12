package ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import uilogic.GridPosition;
import uilogic.GridButtonHandler;

public class MapGridButton extends JButton{
    
    private GridPosition gridPosition;

    public MapGridButton(String text, int gridPositionX, int gridPositionY, GridButtonHandler handler){
        super(text);
        initButton(gridPositionX, gridPositionY, handler);
    }
    
    //#region INITIALIZE
    private void initButton(int x, int y, ActionListener listener){
        initVisuals();
        initData(x, y, listener);
    }

    private void initVisuals(){
        setBounds(0, 0, 100, 100);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
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
