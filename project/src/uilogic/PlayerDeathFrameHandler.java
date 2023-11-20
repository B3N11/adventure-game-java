package uilogic;

import game.global.GameHandler;
import game.utility.delegates.GenericDelegate;
import ui.elements.PlayerDeathFrame;

public class PlayerDeathFrameHandler extends MultipleButtonHandler{

    @Override
    protected void initActions() {
        actions.put("LOAD_SAVE", new GenericDelegate() {
            public void run(Object o){
                var frame = (PlayerDeathFrame)o;
                frame.dispose();
                UIHandler.getInstance().openFileDialog(FileChooserType.PLAYERPROGRESS);
            }
        });

        actions.put("QUIT_GAME", new GenericDelegate() {
            public void run(Object o){ GameHandler.getInstance().quitGame(true); }
        });
    }    
}