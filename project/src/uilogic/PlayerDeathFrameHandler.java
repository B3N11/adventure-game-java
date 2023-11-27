package uilogic;

import game.global.GameHandler;
import game.global.UIHandler;
import game.utility.GenericDelegate;

/**
 * This class handles the actions of the player death frame in the UI.
 * It extends the MultipleButtonHandler class and overrides the initActions method to initialize the actions of the player death frame.
 * 
 * The class contains the following methods:
 * - initActions: Initializes the actions of the player death frame.
 */
public class PlayerDeathFrameHandler extends MultipleButtonHandler{

    /**
     * Initializes the actions of the player death frame.
     * The actions include loading a save and quitting the game.
     * Each action is associated with a key in the actions HashMap of the MultipleButtonHandler class.
     */
    @Override
    protected void initActions() {
        actions.put("LOAD_SAVE", new GenericDelegate() {
            public void run(Object o){
                UIHandler.getInstance().openFileDialog(FileChooserType.PLAYERPROGRESS);
            }
        });

        actions.put("QUIT_GAME", new GenericDelegate() {
            public void run(Object o){ GameHandler.getInstance().quitGame(true); }
        });
    }   
}