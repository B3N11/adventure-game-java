package uilogic;

import game.global.GameHandler;
import game.utility.delegates.GenericDelegate;

public class PlayerDeathFrameHandler extends MultipleButtonHandler{

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