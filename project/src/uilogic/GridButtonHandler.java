package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.general.ArgumentNullException;
import game.utility.GenericDelegate;
import ui.elements.GridButton;

public class GridButtonHandler implements ActionListener {

    private GenericDelegate delegate;
    private GridButton lastSelected;
    private boolean highlightButton;

    public GridButtonHandler(GenericDelegate delegate, boolean highlightButton) throws ArgumentNullException{
        if(delegate == null)
            throw new ArgumentNullException();

        this.delegate = delegate;
        this.highlightButton = highlightButton;
    }

    public void clearSelected(){
        if(lastSelected == null)
            return;

        lastSelected.hightlightButton(false);
        lastSelected.getParent().revalidate();
        lastSelected.getParent().repaint();
        lastSelected = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(highlightButton)
            clearSelected();

        var button = (GridButton)e.getSource();
        lastSelected = button;

        if(highlightButton){
            button.hightlightButton(true);
            button.getParent().revalidate();
            button.getParent().repaint();
        }

        var buttonPosition = ((GridButton)e.getSource()).getGridPosition();
        delegate.run(buttonPosition);
    }    
}
