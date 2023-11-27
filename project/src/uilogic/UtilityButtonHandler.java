package uilogic;

import game.global.UIHandler;
import game.utility.GenericDelegate;

public class UtilityButtonHandler extends MultipleButtonHandler{

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