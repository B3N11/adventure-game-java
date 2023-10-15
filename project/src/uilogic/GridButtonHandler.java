package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.elements.GridButton;

public class GridButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        //Get position
        var buttonPosition = ((GridButton)e.getSource()).getGridPosition();
        System.out.println(buttonPosition.getX() + ";" + buttonPosition.getY());
    }    
}
