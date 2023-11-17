package game.global;

import javax.swing.JOptionPane;

import exception.dice.InvalidDiceSideCountException;
import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import game.behaviour.entities.enemy.Enemy;
import game.global.storage.ActiveEnemyStorage;
import uilogic.UIHandler;

public class GameActionController {
    
    //Use active enemies
    //Place entities
    //Control turns

    public void runEnemyTurns(){
        
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
        var player = GameHandler.getInstance().getPlayer();
        var selectedTile = UIHandler.getInstance().getPlayFieldHandler().getSelectedTile();

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

        if(selectedTile == null)
            return;

        String enemyID = "";
        try{ enemyID = UIHandler.getInstance().getPlayFieldHandler().getEntityByPosition(selectedTile); }
        catch(ElementNotFoundException e){ return; }
        catch(ArgumentNullException e){ /*Wont happen*/ }

        Enemy enemy = null;
        try{ enemy = ActiveEnemyStorage.getInstance().get(enemyID); }
        catch(ArgumentNullException e){ /*Wont happen*/ }

        boolean successfullAttack = false;
        try{ successfullAttack = player.attack(enemy.getEntity().getArmorClass(), distance); }
        catch(Exception e){ UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE);}

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

        message = "Damaging enemy {" + enemy.getEntity().getName() + "}\n";
        int damage = 0;
        try{ damage = player.damage(distance); }
        catch(Exception e){ UIHandler.getInstance().showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE); }

        boolean enemyDied = false;  //TODO: implement enemy dying and modifying enemy
        try{ enemyDied = enemy.takeDamage(damage); }
        catch(InvalidArgumentException e){}

        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(player.getName(), message);}
        catch(ArgumentNullException e){}

        message = "Takes damage {" + damage + "}";  //TODO: WEAPON REWORK: weapon calculated damage should be accessible
        try{ UIHandler.getInstance().getCombatLogger().addEntityLog(enemy.getEntity().getName(), message);}
        catch(ArgumentNullException e){}
    }

    public void playerEndTurnAction(){

    }
}