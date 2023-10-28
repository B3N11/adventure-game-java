package game.global;

public class GameHandler {
    
    private static GameHandler instance;

    private SaveHandler saveHandler;

    private GameHandler(){
        saveHandler = new SaveHandler();
    }

    public static GameHandler getInstance(){
        if(instance == null)
            instance = new GameHandler();
        
        return instance;
    }

    public SaveHandler getSaveHandler() { return saveHandler; }
}
