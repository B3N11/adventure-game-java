package game.global;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import file.handlers.FileHandler;
import game.behaviour.entities.Player;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.ModifiedEnemyStorage;
import game.logic.GameActionController;
import game.utility.dataclass.ModifiedEnemyData;
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
    private HashMap<String, String> maps;   //TODO: read all maps <ID, NAME>

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

    public void handleEnemyDeath(Enemy enemy) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        try{ ActiveEnemyStorage.getInstance().remove(enemy.getInstanceID()); }
        catch(ArgumentNullException e){}

        UIHandler.getInstance().getPlayFieldHandler().removeEntity(enemy.getInstanceID());
        UIHandler.getInstance().refreshUI();

        String message ="Enemy died.";
        UIHandler.getInstance().getCombatLogger().addEntityLog(enemy.getEntity().getName(), message);

        var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getInstanceID());

        if(modifiedData == null){
            modifiedData = new ModifiedEnemyData(enemy.getInstanceID(), enemy.getPosition(), enemy.getCurrentHealth(), false, true);
            ModifiedEnemyStorage.getInstance().add(enemy.getInstanceID(), modifiedData);
        }
        else{
            modifiedData.setDead(true);
            modifiedData.setHealth(enemy.getCurrentHealth());
            modifiedData.setPosition(enemy.getPosition());
        }

        //Add XP for player
        int rewardXP = ((EnemyEntity)enemy.getEntity()).getRewardXP();
        player.addXP(rewardXP);
    }

    public void handlePlayerDeath(){
        UIHandler.getInstance().togglePlayerControlls(false);
        UIHandler.getInstance().displayPlayerDeath();
    }

    public void handlePlayerLevelUp(){
        UIHandler.getInstance().showMessage("YOU LEVELED UP!\nYour new level: " + GameHandler.getInstance().getPlayer().getEntity().getLevel(), JOptionPane.INFORMATION_MESSAGE);
    }

    public void quitGame(boolean instant){
        if(instant)
            System.exit(0);

        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit the game?\nYour unsaved progress will be lost!", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(result == JOptionPane.YES_OPTION)
            System.exit(0);
        return;
    }
}