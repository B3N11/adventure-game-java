package game.global;

import exception.general.ArgumentNullException;
import game.behaviour.entities.Player;

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

    public void start(){
        //start UI handler
        //ask for progress file and asset file
        //load progress
    }
}