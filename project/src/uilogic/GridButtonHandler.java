package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.elements.MapGridButton;

public class GridButtonHandler implements ActionListener {

    public void showButtonPosition(GridPosition position){
        System.out.println(position.getX() + ";" + position.getY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Get position
        var buttonPosition = ((MapGridButton)e.getSource()).getGridPosition();
        System.out.println(buttonPosition.getX() + ";" + buttonPosition.getY());
    }    
}
