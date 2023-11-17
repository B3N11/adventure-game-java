package game.global;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import file.handlers.FileHandler;
import game.behaviour.entities.Player;
import uilogic.FileChooserType;
import uilogic.UIHandler;

public class GameHandler {
    
    private static GameHandler instance;

    private GameActionController actionController;
    private SaveHandler saveHandler;
    private Player player;

    //Game Config
    private String gameTitle;
    private String gameCreator;
    private HashMap<String, String> maps;

    private GameHandler(){
        saveHandler = new SaveHandler();
        actionController = new GameActionController();
        maps = new HashMap<String, String>();
    }

    public static GameHandler getInstance(){
        if(instance == null)
            instance = new GameHandler();
        return instance;
    }

    public GameActionController getActionController() { return actionController; }
    public SaveHandler getSaveHandler() { return saveHandler; }
    public Player getPlayer() { return player; }

    public void setSessionPlayer(Player player) throws ArgumentNullException{
        if(player == null)
            throw new ArgumentNullException();

        this.player = player;
    }

    public void start() throws Exception{
        UIHandler.getInstance().start();
    }

    public void handleChosenFile(String filePath, FileChooserType type) throws ArgumentNullException, FileNotFoundException{
        if(filePath == null)
            throw new ArgumentNullException();

        try{
            switch (type) {
                case CONFIG:
                    FileHandler.getInstance().loadConfigFile(filePath);
                    break;
        
                case PLAYERPROGRESS:
                    FileHandler.getInstance().loadPlayerProgressSave(filePath);
                    break;
            }
        }catch(ClassNotFoundException e) { UIHandler.getInstance().showMessage("Given file is not in required format!", JOptionPane.ERROR_MESSAGE);
        }catch(FileNotFoundException e) { UIHandler.getInstance().showMessage("Given file(s) don't exist.", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){ UIHandler.getInstance().showMessage("Error reading the file!", JOptionPane.ERROR_MESSAGE);}
    }
}