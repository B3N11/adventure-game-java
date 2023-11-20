package game.logic;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import game.behaviour.entities.enemy.Enemy;
import game.global.GameHandler;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.ModifiedEnemyStorage;
import game.utility.dataclass.ModifiedEnemyData;
import uilogic.UIHandler;

public class GameActionController {
    
    //Use active enemies
    //Place entities
    //Control turns

    boolean playerAttackedThisTurn;

    public GameActionController(){
        playerAttackedThisTurn = false;
    }

    public void runEnemyTurns(){  
        var entrySet = ActiveEnemyStorage.getInstance().entrySet();
        for(var enemy : entrySet){
            var player = GameHandler.getInstance().getPlayer();

            try{
                double distance = UIHandler.getInstance().getPlayFieldHandler().getDistanceBetweenEntities(player.getInstanceID(), enemy.getValue().getInstanceID());
                enemy.getValue().getEnemyType().getController().runEnemy(player, distance);
            }catch(Exception e){}
        }

        UIHandler.getInstance().togglePlayerControlls(true);
    }

    public void playerMoveAction(){
        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

        if(selectedTile == null)
            return;

        try{
            UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), "Attempted to move...");
            
            UIHandler.getInstance().getPlayFieldHandler().getEntityByPosition(selectedTile);

            String message = "FAIL! There is another entity on the tile!";
            UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);
            return;
        }
        catch(ArgumentNullException e){ /*Wont happen*/ }
        catch(ElementNotFoundException e){ /*Player can safely move there*/ }
        
        double distance = UIHandler.getInstance().getPlayFieldHandler().getSelectedTileDistance();

        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), "Attempted to move..."); }
        catch(ArgumentNullException e){}

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

        try{ player.setPosition(selectedTile); }
        catch(ArgumentNullException e){}
        try{
            UIHandler.getInstance().getPlayFieldHandler().replaceEntity(player.getID(), player.getPosition());
            UIHandler.getInstance().refreshUI();
        }
        catch(Exception e){}
    }

    public void playerAttackAction(){
        if(playerAttackedThisTurn){
            try{ UIHandler.getInstance().getCombatLogger().addSystemLog("Player already attacked this turn."); }
            catch(ArgumentNullException e){}
            return;
        }

        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

        if(selectedTile == null)
            return;

        String enemyID = "";
        try{ enemyID = UIHandler.getInstance().getPlayFieldHandler().getEntityByPosition(selectedTile); }
        catch(ElementNotFoundException e){ return; }
        catch(ArgumentNullException e){ /*Wont happen*/ }

        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), "Attempted to attack..."); }
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
        try{ enemy = ActiveEnemyStorage.getInstance().get(enemyID); }
        catch(ArgumentNullException e){ /*Wont happen*/ }

        //Roll attack dice
        boolean successfullAttack = false;
        try{ successfullAttack = player.attack(enemy.getEntity().getArmorClass(), distance); }
        catch(Exception e){ UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE);}

        playerAttackedThisTurn = true;

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
            //Add enemy to modified enemy
            try{
                var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getInstanceID());

                if(modifiedData == null){
                    modifiedData = new ModifiedEnemyData(enemy.getInstanceID(), enemy.getPosition(), enemy.getCurrentHealth(), false, enemyDied);
                    ModifiedEnemyStorage.getInstance().add(enemy.getInstanceID(), modifiedData);
                }
                else{
                    modifiedData.setHealth(enemy.getCurrentHealth());
                    modifiedData.setPosition(enemy.getPosition());
                    modifiedData.setDead(enemyDied);
                }
            }
            catch(Exception e){}
        }
    }

    public void playerEndTurnAction(){
        playerAttackedThisTurn = false;
        try{ GameHandler.getInstance().getPlayer().resetMovement(); }
        catch(Exception e){}
        UIHandler.getInstance().togglePlayerControlls(false);

        runEnemyTurns();
    }
}