package game.global;

import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import file.elements.MapLayoutData;
import game.GameActionController;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.player.Player;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.ModifiedEnemyStorage;
import game.utility.ModifiedEnemyData;
import uilogic.FileChooserType;

/**
 * This class represents a GameHandler in the game. It is responsible for handling the main game logic.
 * It is a singleton class that handles the game actions and saving.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the GameHandler class.
 * - actionController: The GameActionController that handles the game actions.
 * - saveHandler: The SaveHandler that handles the game saving.
 * - player: The Player of the game.
 * - gameTitle: The title of the game.
 * - gameCreator: The creator of the game.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class GameHandler {
    
    private static GameHandler instance;

    private GameActionController actionController;
    private SaveHandler saveHandler;
    private Player player;

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

    /**
     * Starts the game. Must be called first. When program starts.
     * Initializes the game and displays the main menu.
     */
    public void start() throws Exception{
        UIHandler.getInstance().start();
    }

    /**
     * Sets the current map layout.
     * @param layout The layout to set.
     */
    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        if(data == null)
            throw new ArgumentNullException();
        
        UIHandler.getInstance().getPlayFieldHandler().setCurrentMapLayout(data);
        UIHandler.getInstance().getCombatLogger().addMapDescription(data.getName(), data.getDescription());
    }
    
    /**
     * Handles what happens to the chosen file based on the given type.
     * Loads the game from the chosen file.
     * @param file The chosen file.
     */
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

    /**
     * Handles the death of an enemy. Removes the enemy from the active enemies and updates the UI.
     * Removes the enemy from the active enemies and updates the UI.
     * @param enemy The enemy that died.
     */
    public void handleEnemyDeath(Enemy enemy) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        try{ ActiveEnemyStorage.getInstance().remove(enemy.getInstanceID()); }
        catch(ArgumentNullException e){}

        modifyEnemy(enemy, true);

        UIHandler.getInstance().getPlayFieldHandler().removeEntity(enemy.getInstanceID());
        UIHandler.getInstance().refreshUI();

        String message ="Enemy died.";
        UIHandler.getInstance().getCombatLogger().addEntityLog(enemy.getEntity().getName(), message);

        //Add XP for player
        int rewardXP = ((EnemyEntity)enemy.getEntity()).getRewardXP();
        message = player.getName() + " gained " + rewardXP + " XP.\nPlayer XP: " + (player.getXP() + rewardXP) + "/" + player.getRequiredXP();
        UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);
        
        player.addXP(rewardXP);
    }

    /**
     * Modifies an enemy. Saves the enemy's data and position in the ModifiedEnemyStorage.
     * Updates the enemy's data and position.
     * @param enemy The enemy to modify.
     * @param enemyPosition The new position of the enemy.
     */
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

    /**
     * Handles the death of the player. Disables the player controls and displays the player death screen.
     * Disables the player controls and displays the player death screen.
     */
    public void handlePlayerDeath(){
        UIHandler.getInstance().togglePlayerControlls(false);
        UIHandler.getInstance().displayPlayerDeath();
    }

    /**
     * Handles the level up of the player. Displays a message indicating the player has leveled up.
     * Displays a message indicating the player has leveled up.
     */
    public void handlePlayerLevelUp(){
        UIHandler.getInstance().showMessage("YOU LEVELED UP!\nYour new level: " + GameHandler.getInstance().getPlayer().getEntity().getLevel(), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Quits the game. Prompts the player for confirmation before quitting.
     * If instant is true, it quits the game immediately.
     * Otherwise, it asks the player for confirmation before quitting.
     * @param instant Whether to quit the game immediately.
     */
    public void quitGame(boolean instant){
        if(instant)
            System.exit(0);

        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit the game?\nYour unsaved progress will be lost!", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(result == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    /**
     * Checks the player's condition for action. And wheter the player is prevented from executing an action.
     * If the player is dead, it logs a message and returns true.
     * Otherwise, it returns false.
     * @return Whether the player is dead.
     */
    public boolean checkPlayerConditionForAction(){
        if(!player.isDead())
            return false;

        try{ UIHandler.getInstance().getCombatLogger().addSystemLog("Action cannot be executed, player is dead."); }
        catch(ArgumentNullException e){}

        return true;
    }
}