package uilogic;

import javax.swing.JOptionPane;

import exception.save.CurrentSaveUnmodifiableException;
import game.global.GameHandler;
import game.global.UIHandler;
import game.utility.GenericDelegate;

public class PlayFrameMenuBarHandler extends MultipleButtonHandler{

    public PlayFrameMenuBarHandler(){
        initActions();
    }

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
