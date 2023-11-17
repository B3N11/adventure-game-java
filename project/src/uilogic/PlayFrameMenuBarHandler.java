package uilogic;

import game.global.GameHandler;
import game.utility.delegates.GenericDelegate;

public class PlayFrameMenuBarHandler extends MultipleButtonHandler{

    public PlayFrameMenuBarHandler(){
        initActions();
    }

    @Override
    protected final void initActions(){
        var saveHandler = GameHandler.getInstance().getSaveHandler();

        actions.put("QUICK_SAVE_GAME", new GenericDelegate() {
           public void run(Object o){ saveHandler.quickSave(); } 
        });

        actions.put("LOAD_CONFIGFILE", new GenericDelegate(){
            public void run(Object o){ UIHandler.getInstance().openFileDialog(FileChooserType.CONFIG); }
        });

        actions.put("LOAD_GAME", new GenericDelegate() {
            public void run(Object o){ UIHandler.getInstance().openFileDialog(FileChooserType.PLAYERPROGRESS); }
        });
    }
}
