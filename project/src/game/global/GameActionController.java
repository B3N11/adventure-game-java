package game.global;

import javax.swing.JOptionPane;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import ui.data.GridPosition;
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

        boolean successfulMove = player.move(distance);

        if(!successfulMove){
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

    }

    public void playerEndTurnAction(){

    }
}