package uilogic;

import java.io.IOException;
import java.util.HashMap;

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

    private HashMap<String, IInteractiveEntity> currentEntitiesOnMap;

    public PlayFieldHandler(PlayfieldPanel playField){
        this.playField = playField;
        currentEntitiesOnMap = new HashMap<String, IInteractiveEntity>();

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

        String entityType = "EMPTY";
        try{ 
            var entity = getEntityByPosition(selectedTile);
            entityType = entity.getEntity().getEntityType().toString();
        }catch(ElementNotFoundException e){
        }catch(ArgumentNullException e){}

        try{ selectedTileDistance = GridPosition.calculateAbsoluteDistance(GameHandler.getInstance().getPlayer().getPosition(), selectedTile); }
        catch(ArgumentNullException e){}

        String message = "Tile selected: {distance=" + selectedTileDistance + "}\n" +
                         "Tile type: " + entityType;

        try{ UIHandler.getInstance().getCombatLogger().addSystemLog(message); }
        catch(ArgumentNullException e){}
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        playField.setMapLayout(data, gridButtonHandler, true);
        currentMapLayoutData = data;
    }

    public void placeEntity(IInteractiveEntity entity, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(entity.getInstanceID(),
            playField.getComponentSize().getHorizontal(),
            playField.getComponentSize().getVertical(),
            entity.getPosition());
        component.setImage(imagePath);

        playField.addEntity(component);
        currentEntitiesOnMap.put(entity.getInstanceID(), entity);
    }

    public void replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        playField.replaceEntity(id, newPosition);
    }

    public IInteractiveEntity getEntityByPosition(GridPosition position) throws ElementNotFoundException, ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
            
        for(var entity : currentEntitiesOnMap.entrySet())
            if(entity.getValue().getPosition().equals(position))
                return entity.getValue();
        throw new ElementNotFoundException();
    }
}