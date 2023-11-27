package uilogic;

import game.global.GameHandler;
import game.utility.GenericDelegate;

/**
 * This class handles the actions of interaction buttons in the UI. Used for the InteractButtonPanel.
 * It extends the MultipleButtonHandler class and overrides the initActions method to initialize the actions of the interaction buttons.
 * 
 * The class contains the following methods:
 * - initActions: Initializes the actions of the interaction buttons.
 */
public class InteractButtonHandler extends MultipleButtonHandler{

    /**
     * Initializes the actions of the interaction buttons.
     * The actions include moving the player, ending the player's turn, attacking with the player, and picking up with the player.
     * Each action is associated with a key in the actions HashMap of the MultipleButtonHandler class.
     */
    @Override
    protected void initActions() {
        actions.put("INTERACT_MOVE", new GenericDelegate() {
            public void run(Object o) { GameHandler.getInstance().getActionController().playerMoveAction(); }           
        });

        actions.put("INTERACT_ENDTURN", new GenericDelegate() {
            public void run(Object o) { GameHandler.getInstance().getActionController().playerEndTurnAction(); }           
        });

        actions.put("INTERACT_ATTACK", new GenericDelegate() {
            public void run(Object o) { GameHandler.getInstance().getActionController().playerAttackAction(); }           
        });

        actions.put("INTERACT_PICKUP", new GenericDelegate() {
            public void run(Object o) { GameHandler.getInstance().getActionController().playerPickUpAction(); }
        });
    }   
}