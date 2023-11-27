package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import game.utility.GenericDelegate;

/**
 * This abstract class handles multiple buttons in the UI.
 * It implements ActionListener and contains a HashMap of actions for the buttons.
 * Each subclass must implement the initActions method to initialize the actions of the buttons.
 * 
 * The class contains the following fields:
 * - actions: The HashMap of actions for the buttons.
 */
public abstract class MultipleButtonHandler implements ActionListener{
    
    protected HashMap<String, GenericDelegate> actions;

    /**
     * Constructor for the MultipleButtonHandler class.
     * Initializes the MultipleButtonHandler with a HashMap of actions and calls the initActions method to initialize the actions of the buttons.
     */
    protected MultipleButtonHandler(){
        actions = new HashMap<>();
        initActions();
    }

    /**
     * Initializes the actions of the buttons.
     * This method must be implemented by each subclass of the MultipleButtonHandler class.
     */
    protected abstract void initActions();

    /**
     * Handles the action performed by a button.
     * Gets the action associated with the button from the actions HashMap and runs the action.
     * @param e The ActionEvent object representing the action performed by the button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        var action = actions.get(e.getActionCommand());

        if(action == null)
            return;
        action.run(null);
    }
}