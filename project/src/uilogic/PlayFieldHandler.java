package uilogic;

import java.io.IOException;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import game.behaviour.interfaces.IInteractiveEntity;
import game.global.GameHandler;
import game.utility.dataclass.MapLayoutData;
import game.utility.delegates.GenericDelegate;
import ui.data.GridPosition;
import ui.elements.GridEntityComponent;
import ui.elements.PlayfieldPanel;

public class PlayFieldHandler {
    
    private PlayfieldPanel playField;
    private GridButtonHandler gridButtonHandler;

    private GridPosition selectedTile;
    private double selectedTileDistance;

    private MapLayoutData currentMapLayoutData;

    public PlayFieldHandler(PlayfieldPanel playField){
        this.playField = playField;

        try{
            gridButtonHandler = new GridButtonHandler(new GenericDelegate() {
                public void run(Object o) { selectTile(o); }
            });
        }catch(ArgumentNullException e){}
    }

    public PlayfieldPanel getPlayField() { return playField; }
    public GridButtonHandler getGridButtonHandler() { return gridButtonHandler; }
    public MapLayoutData getCurrentMapLayoutData() { return currentMapLayoutData; }
    public GridPosition getSelectedTile() { return selectedTile; }
    public double getSelectedTileDistance() { return selectedTileDistance; }

    public void setPlayField(PlayfieldPanel playField) throws ArgumentNullException{
        if(playField == null)
            throw new ArgumentNullException();
        this.playField = playField;
    }

    public void selectTile(Object o){            
        selectedTile = (GridPosition)o;

        try{
            var playerPosition = playField.getEntity(GameHandler.getInstance().getPlayer().getInstanceID()).getGridPosition();
            selectedTileDistance = GridPosition.calculateAbsoluteDistance(playerPosition, selectedTile);
        }
        catch(ArgumentNullException e){}
        catch(ElementNotFoundException e){}
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        playField.setMapLayout(data, gridButtonHandler, true);
        currentMapLayoutData = data;
    }

    public void placeEntity(String id, GridPosition position, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(id,
            playField.getComponentSize().getHorizontal(),
            playField.getComponentSize().getVertical(),
            position);
        component.setImage(imagePath);

        playField.addEntity(component);
    }

    public void removeEntity(String id) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(id == null)
            throw new ArgumentNullException();
        
        playField.removeEntity(id, true);
    }

    public void replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        playField.replaceEntity(id, newPosition);
    }

    public GridPosition getEntityPositionByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();
        return playField.getEntity(id).getGridPosition();
    }

    public String getEntityIDByPosition(GridPosition position) throws ElementNotFoundException, ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
            
        return playField.getEntityByPosition(position);
    }

    public double getDistanceBetweenEntities(String srcID, String dstID) throws ArgumentNullException, ElementNotFoundException{
        if(srcID == null || dstID == null)
            throw new ArgumentNullException();

        var srcEntity = playField.getEntity(srcID);
        var dstEntity = playField.getEntity(dstID);
        double result = GridPosition.calculateAbsoluteDistance(srcEntity.getGridPosition(), dstEntity.getGridPosition());
        return result;
    }
}