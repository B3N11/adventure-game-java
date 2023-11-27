package uilogic;

import game.global.GameHandler;
import game.utility.GenericDelegate;

public class InteractButtonHandler extends MultipleButtonHandler{

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