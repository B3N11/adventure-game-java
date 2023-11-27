package game.logic;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import game.behaviour.Item;
import game.behaviour.entities.enemy.Enemy;
import game.global.GameHandler;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.ItemStorage;
import uilogic.UIHandler;

public class GameActionController {

    int playerAttacksLeft;

    public void resetPlayerAttacksLeft(){
        playerAttacksLeft = GameHandler.getInstance().getPlayer().getEntity().getWeapon().getAttacksInRound();
    }

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
        message += damage;

        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);}
        catch(ArgumentNullException e){}

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
    }

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