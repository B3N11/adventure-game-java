package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.general.ArgumentNullException;
import game.utility.delegates.GenericDelegate;
import ui.elements.GridButton;

public class GridButtonHandler implements ActionListener {

    private GenericDelegate delegate;

    public GridButtonHandler(GenericDelegate delegate) throws ArgumentNullException{
        if(delegate == null)
            throw new ArgumentNullException();

        this.delegate = delegate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var buttonPosition = ((GridButton)e.getSource()).getGridPosition();
        delegate.run(buttonPosition);
    }    
}
