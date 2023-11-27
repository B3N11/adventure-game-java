package main.uilogic;

import javax.swing.JOptionPane;

import main.exception.save.CurrentSaveUnmodifiableException;
import main.game.global.GameHandler;
import main.game.global.UIHandler;
import main.game.utility.GenericDelegate;

/**
 * This class handles the actions of the menu bar in the play frame.
 * It extends the MultipleButtonHandler class and overrides the initActions method to initialize the actions of the menu bar.
 * 
 * The class contains the following methods:
 * - initActions: Initializes the actions of the menu bar.
 */
public class PlayFrameMenuBarHandler extends MultipleButtonHandler{
    /**
     * Initializes the actions of the menu bar.
     * The actions include quick saving the game, creating a new save game, loading a config file, and loading a game.
     * Each action is associated with a key in the actions HashMap of the MultipleButtonHandler class.
     */
    @Override
    protected final void initActions(){
        var saveHandler = GameHandler.getInstance().getSaveHandler();

        actions.put("QUICK_SAVE_GAME", new GenericDelegate() {
           public void run(Object o){
            try{ saveHandler.quickSave(); }
            catch(CurrentSaveUnmodifiableException e){
                UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        } 
        });

        actions.put("NEW_SAVE_GAME", new GenericDelegate() {
            public void run(Object o){
                UIHandler.getInstance().openFileDialog(FileChooserType.NEWSAVE);
            }
        });

        actions.put("LOAD_CONFIGFILE", new GenericDelegate(){
            public void run(Object o){ UIHandler.getInstance().openFileDialog(FileChooserType.CONFIG); }
        });

        actions.put("LOAD_GAME", new GenericDelegate() {
            public void run(Object o){ UIHandler.getInstance().openFileDialog(FileChooserType.PLAYERPROGRESS); }
        });
    }
}
