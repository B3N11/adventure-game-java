package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import game.global.GameHandler;

public class PlayFrameMenuBarHandler implements ActionListener{

    private HashMap<String, Runnable> menuActions;

    public PlayFrameMenuBarHandler(){
        initMenuActions();
    }

    private void initMenuActions(){
        menuActions = new HashMap<String, Runnable>();

        var saveHandler = GameHandler.getInstance().getSaveHandler();

        menuActions.put("QUICK_SAVE_GAME", new Runnable() {
           public void run(){ saveHandler.quickSave(); } 
        });

        menuActions.put("LOAD_CONFIGFILE", new Runnable(){
            public void run(){ UIHandler.getInstance().openFileDialog(FileChooserType.BASEINFO); }
        });

        menuActions.put("LOAD_GAME", new Runnable() {
            public void run(){ UIHandler.getInstance().openFileDialog(FileChooserType.PLAYERPROGRESS); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = menuActions.get(e.getActionCommand());

        if(action == null)
            return;
        action.run();
    }
}
