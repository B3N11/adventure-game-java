package uilogic;

import game.global.UIHandler;
import game.utility.GenericDelegate;

/**
 * This class handles the actions of the utility buttons in the UI.
 * It extends the MultipleButtonHandler class and overrides the initActions method to initialize the actions of the utility buttons.
 * 
 * The class contains the following methods:
 * - initActions: Initializes the actions of the utility buttons.
 */
public class UtilityButtonHandler extends MultipleButtonHandler{

    /**
     * Initializes the actions of the utility buttons.
     * The actions include displaying the character frame and the travel frame.
     * Each action is associated with a key in the actions HashMap of the MultipleButtonHandler class.
     */
    @Override
    protected void initActions() {
        actions.put("UTILITY_EQUIPMENT", new GenericDelegate() {
            public void run(Object o) { UIHandler.getInstance().displayCharacterFrame(); }           
        });

        actions.put("UTILITY_TRAVEL", new GenericDelegate() {
            public void run(Object o) { UIHandler.getInstance().displayTravelFrame(); }
        });
    }  
}