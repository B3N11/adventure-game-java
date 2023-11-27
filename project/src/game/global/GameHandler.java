package game.global;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import file.elements.MapLayoutData;
import file.handlers.FileHandler;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.player.Player;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.ModifiedEnemyStorage;
import game.logic.GameActionController;
import game.utility.ModifiedEnemyData;
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

    private GameHandler(){
        saveHandler = new SaveHandler();
        actionController = new GameActionController();
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
        actionController.resetPlayerAttacksLeft();
    }

    public void start() throws Exception{
        UIHandler.getInstance().start();
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        if(data == null)
            throw new ArgumentNullException();
        
        UIHandler.getInstance().getPlayFieldHandler().setCurrentMapLayout(data);
        UIHandler.getInstance().getCombatLogger().addMapDescription(data.getName(), data.getDescription());
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

                case NEWSAVE:
                    saveHandler.save(filePath, true);
                    break;

            }
        }catch(ClassNotFoundException e) { UIHandler.getInstance().showMessage("Given file is not in required format!", JOptionPane.ERROR_MESSAGE);
        }catch(FileNotFoundException e) { UIHandler.getInstance().showMessage("Given file(s) don't exist.", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){ UIHandler.getInstance().showMessage("Error reading the file!", JOptionPane.ERROR_MESSAGE);}

        UIHandler.getInstance().togglePlayerControlls(true);
    }

    public void handleEnemyDeath(Enemy enemy) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        try{ ActiveEnemyStorage.getInstance().remove(enemy.getInstanceID()); }
        catch(ArgumentNullException e){}

        modifyEnemy(enemy, true);

        UIHandler.getInstance().getPlayFieldHandler().removeEntity(enemy.getInstanceID());
        UIHandler.getInstance().refreshUI();

        String message ="Enemy died.";
        UIHandler.getInstance().getCombatLogger().addEntityLog(enemy.getEntity().getName(), message);

        //Add XP for player
        int rewardXP = ((EnemyEntity)enemy.getEntity()).getRewardXP();
        player.addXP(rewardXP);
    }

    public void modifyEnemy(Enemy enemy, boolean dead) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        try{
            var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getInstanceID());
            var enemyPosition = UIHandler.getInstance().getPlayFieldHandler().getEntityPositionByID(enemy.getInstanceID());
    
            if(modifiedData == null){
                modifiedData = new ModifiedEnemyData(enemy.getInstanceID(), enemyPosition, enemy.getCurrentHealth(), false, dead);
                ModifiedEnemyStorage.getInstance().add(enemy.getInstanceID(), modifiedData);
            }
            else{
                modifiedData.setDead(true);
                modifiedData.setHealth(enemy.getCurrentHealth());
                modifiedData.setPosition(enemyPosition);
            }
        }catch(Exception e){}
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
    }

    public boolean checkPlayerConditionForAction(){
        if(!player.isDead())
            return false;

        try{ UIHandler.getInstance().getCombatLogger().addSystemLog("Action cannot be executed, player is dead."); }
        catch(ArgumentNullException e){}

        return true;
    }
}