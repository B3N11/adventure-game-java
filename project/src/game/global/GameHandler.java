package game.global;

import java.io.FileNotFoundException;

import exception.general.ArgumentNullException;
import file.handlers.FileHandler;
import game.behaviour.entities.Player;
import uilogic.FileChooserType;
import uilogic.UIHandler;

public class GameHandler {
    
    private static GameHandler instance;

    private SaveHandler saveHandler;
    private Player player;

    private GameHandler(){
        saveHandler = new SaveHandler();
    }

    public static GameHandler getInstance(){
        if(instance == null)
            instance = new GameHandler();
        return instance;
    }

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

        switch (type) {
            case BASEINFO:
                FileHandler.getInstance().startHandler(filePath);
                break;
        
            default:
                break;
        }
    }
}