package main.game;

import javax.swing.JOptionPane;

import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.exception.general.InvalidArgumentException;
import main.game.behaviour.entities.enemy.Enemy;
import main.game.behaviour.items.Item;
import main.game.global.GameHandler;
import main.game.global.UIHandler;
import main.game.global.storage.ActiveEnemyStorage;
import main.game.global.storage.ItemStorage;

/**
 * This class controls the actions in the game.
 * It contains methods to reset the player's attacks left, run the enemy turns, and handle the player's pick up action.
 * 
 * The class contains the following fields:
 * - playerAttacksLeft: The number of attacks left for the player.
 */
public class GameActionController {

    private int playerAttacksLeft;

    /**
     * Resets the number of attacks left for the player.
     * The number of attacks left is set to the number of attacks in a round of the player's weapon.
     */
    public void resetPlayerAttacksLeft(){
        playerAttacksLeft = GameHandler.getInstance().getPlayer().getEntity().getWeapon().getAttacksInRound();
    }

    /**
     * Runs the turns for all the active enemies in ActiveEnemyStorage.
     * For each enemy, if the player is not dead, the enemy's controller runs the enemy's turn.
     * After all the enemy turns, the player controls are toggled on.
     */
    public void runEnemyTurns(){  
        var entrySet = ActiveEnemyStorage.getInstance().entrySet();
        for(var enemy : entrySet){
            var player = GameHandler.getInstance().getPlayer();

            if(player.isDead())
                break;

            try{
                double distance = UIHandler.getInstance().getPlayFieldHandler().getDistanceBetweenEntities(player.getInstanceID(), enemy.getValue().getInstanceID());
                enemy.getValue().getEnemyType().getController().runEnemy(player, distance);
            }catch(Exception e){}
        }

        UIHandler.getInstance().togglePlayerControlls(true);
    }

    /**
     * Handles the player's action of picking up an item.
     * If the player is in a condition that allows the action, the action is performed.
     */
    public void playerPickUpAction(){
        if(GameHandler.getInstance().checkPlayerConditionForAction())
            return;

        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

        if(selectedTile == null)
            return;

        try{ 
            String entityID = UIHandler.getInstance().getPlayFieldHandler().getEntityIDByPosition(selectedTile);
            double distance = UIHandler.getInstance().getPlayFieldHandler().getSelectedTileDistance();

            if(!ItemStorage.getInstance().contains(entityID)){
                try{ UIHandler.getInstance().getCombatLogger().addSystemLog("FAIL! Selected entity is not an item!"); }
                catch(ArgumentNullException e){}
                return;
            }

            if(distance > 1){
                try{ UIHandler.getInstance().getCombatLogger().addSystemLog("FAIL! Selected tile is too far! You need to be on an adjacent tile."); }
                catch(ArgumentNullException e){}
                return;
            }

            Item item = ItemStorage.getInstance().get(entityID);
            player.getEntity().getInventory().add(item);
            try{ UIHandler.getInstance().getPlayFieldHandler().removeEntity(entityID); }
            catch(InvalidArgumentException e){ /* Wont happen */}
        }
        catch(ElementNotFoundException e){ return; }
        catch(ArgumentNullException e){ /* Wont happen */}
    }

    /**
     * Handles the player's action of moving.
     * If the player is in a condition that allows movement, the player is moved to the selected tile.
     */
    public void playerMoveAction(){
        if(GameHandler.getInstance().checkPlayerConditionForAction())
            return;

        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

        if(selectedTile == null)
            return;

        try{
            //UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), "Attempted to move...");
            
            UIHandler.getInstance().getPlayFieldHandler().getEntityIDByPosition(selectedTile);

            String message = "FAIL! There is another entity on the tile!";
            UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);
            return;
        }
        catch(ArgumentNullException e){ /*Wont happen*/ }
        catch(ElementNotFoundException e){ /*Player can safely move there*/ }
        
        double distance = UIHandler.getInstance().getPlayFieldHandler().getSelectedTileDistance();

        boolean successfullMove = player.move(distance);

        if(!successfullMove){
            String message = "FAIL! No more movement left. \n" + "Selected distance: " + distance + "\nRemaining movement: " + player.getCurrentMovement();
            try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message); }
            catch(ArgumentNullException e){}
            return;
        }

        String message = "SUCCESS! Remaining movement: " + player.getCurrentMovement();
        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message); }
        catch(ArgumentNullException e){}

        try{
            UIHandler.getInstance().getPlayFieldHandler().replaceEntity(player.getID(), selectedTile);
            UIHandler.getInstance().refreshUI();
        }
        catch(Exception e){}
    }

    /**
     * Handles the player's action of attacking an enemy.
     * If the player is in a condition that allows attacking and the selected tile contains an enemy, the player attacks the enemy.
     * If the enemy dies from the attack, the enemy death is handled.
     * If the enemy does not die, the enemy data is modified.
     */
    public void playerAttackAction(){
        if(GameHandler.getInstance().checkPlayerConditionForAction())
            return;

        if(playerAttacksLeft == 0){
            try{ UIHandler.getInstance().getCombatLogger().addSystemLog("Player has no more attacks in this turn."); }
            catch(ArgumentNullException e){}
            return;
        }

        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

        if(selectedTile == null)
            return;

        String enemyID = "";
        try{ enemyID = UIHandler.getInstance().getPlayFieldHandler().getEntityIDByPosition(selectedTile); }
        catch(ElementNotFoundException e){ return; }
        catch(ArgumentNullException e){ /*Wont happen*/ }

        double distance = UIHandler.getInstance().getPlayFieldHandler().getSelectedTileDistance();
        var weapon = player.getEntity().getWeapon();

        if(!weapon.checkRange(distance)){
            String message = "FAIL! Enemy out of range.\n" + "Selected distance: " + distance + "\nWeapon reach: " + weapon.getRange();
            try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message); }
            catch(ArgumentNullException e){}
            return;
        }

        Enemy enemy = null;
        try{
            enemy = ActiveEnemyStorage.getInstance().get(enemyID);
            if(enemy == null)
                throw new ElementNotFoundException();
        }
        catch(ArgumentNullException e){ /*Wont happen*/ }
        catch(ElementNotFoundException e){ return; }

        //Roll attack dice
        boolean successfullAttack = false;
        try{ successfullAttack = player.attack(enemy.getEntity().getArmorClass(), distance); }
        catch(Exception e){ UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE);}

        playerAttacksLeft--;

        String message = "";
        try{ message = "Target AC: " + enemy.getEntity().getArmorClass() + "\n";}
        catch(Exception e){ /*Wont happen*/ }

        if(!successfullAttack){
            try{
                message += " FAIL";
                UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);
            }
            catch(Exception e){ /*Wont happen*/}
            return;
        }

        message += " SUCCESS";
        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message); }
        catch(ArgumentNullException e){}

        //Roll for damage
        message = "Damaging enemy {" + enemy.getEntity().getName() + "}\nDamage: ";
        int damage = 0;
        try{ damage = player.damage(distance); }
        catch(Exception e){ UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE); }

        //Take damage anc check if enemy died
        boolean enemyDied = false;
        try{ enemyDied = enemy.takeDamage(damage); }
        catch(InvalidArgumentException e){}

        if(enemyDied){
            try{ GameHandler.getInstance().handleEnemyDeath(enemy); }
            catch(Exception e){}
        }
        else{
            try{ GameHandler.getInstance().modifyEnemy(enemy, false); }
            catch(Exception e){}
        }

        message += damage + "\nEnemy health: " + enemy.getCurrentHealth() + "/" + enemy.getEntity().getHealth();
        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);}
        catch(ArgumentNullException e){}
    }

    /**
     * Handles the player's action of ending their turn.
     * If the player is in a condition that allows ending their turn, the player's attacks left and movement are reset, the player controls are toggled off, and the enemy turns are run.
     */
    public void playerEndTurnAction(){
        if(GameHandler.getInstance().checkPlayerConditionForAction())
            return;

        resetPlayerAttacksLeft();
        try{ GameHandler.getInstance().getPlayer().resetMovement(); }
        catch(Exception e){}
        UIHandler.getInstance().togglePlayerControlls(false);

        runEnemyTurns();
    }
}