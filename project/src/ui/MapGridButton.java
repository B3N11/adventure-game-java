package ui;

import javax.swing.JButton;

import uilogic.GridPosition;
import uilogic.GridButtonHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapGridButton extends JButton implements ActionListener{
    
    private GridPosition gridPosition;
    private GridButtonHandler handler;

    public MapGridButton(String text, int gridPositionX, int gridPositionY, GridButtonHandler handler){
        super(text);
        this.handler = handler;
        initButton(gridPositionX, gridPositionY);
    }
    
    //#region INITIALIZE
    private void initButton(int x, int y){
        initVisuals();
        initData(x, y);
    }

    private void initVisuals(){
        setBounds(0, 0, 100, 100);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(this);
    }

    private void initData(int x, int y){
        gridPosition = new GridPosition(x, y);
    }
    //#endregion

    //#region GETTERS
    public GridPosition geGridPosition(){
        return gridPosition;
    }
    //#endregion

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("ASD");
        handler.showButtonPosition(gridPosition);
    }
}
