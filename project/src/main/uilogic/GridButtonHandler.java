package main.uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.exception.general.ArgumentNullException;
import main.game.utility.GenericDelegate;
import main.ui.elements.GridButton;

/**
 * This class handles the actions of grid buttons in the UI.
 * It implements the ActionListener interface and contains a GenericDelegate for handling grid button actions, a GridButton for the last selected grid button, and a boolean for whether to highlight the selected grid button.
 * 
 * The class contains the following fields:
 * - delegate: The GenericDelegate for handling grid button actions.
 * - lastSelected: The GridButton for the last selected grid button.
 * - highlightButton: The boolean for whether to highlight the selected grid button.
 */
public class GridButtonHandler implements ActionListener {

    private GenericDelegate delegate;
    private GridButton lastSelected;
    private boolean highlightButton;

    /**
     * Constructor for the GridButtonHandler class.
     * Initializes the GridButtonHandler with a GenericDelegate for handling grid button actions and a boolean for whether to highlight the selected grid button.
     * @param delegate The GenericDelegate for handling grid button actions.
     * @param highlightButton The boolean for whether to highlight the selected grid button on press.
     * @throws ArgumentNullException if the delegate is null.
     */
    public GridButtonHandler(GenericDelegate delegate, boolean highlightButton) throws ArgumentNullException{
        if(delegate == null)
            throw new ArgumentNullException();

        this.delegate = delegate;
        this.highlightButton = highlightButton;
    }

    /**
     * Clears the selected grid button.
     * If the last selected grid button is not null, unhighlights the last selected grid button, revalidates and repaints its parent, and sets the last selected grid button to null.
     */
    public void clearSelected(){
        if(lastSelected == null)
            return;

        lastSelected.hightlightButton(false);
        lastSelected.getParent().revalidate();
        lastSelected.getParent().repaint();
        lastSelected = null;
    }

    /**
     * Handles the action of a grid button.
     * If the highlightButton field is true, clears the selected grid button, gets the source of the action event, sets the last selected grid button to the source, and highlights the last selected grid button.
     * @param e The action event.
     */
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
