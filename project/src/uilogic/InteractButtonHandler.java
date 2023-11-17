package uilogic;

import game.global.GameHandler;
import game.utility.delegates.GenericDelegate;

public class InteractButtonHandler extends MultipleButtonHandler{

    @Override
    protected void initActions() {
        
        actions.put("INTERACT_MOVE", new GenericDelegate() {
            public void run(Object o) { GameHandler.getInstance().getActionController().playerMoveAction(); }           
        });
    }   
}