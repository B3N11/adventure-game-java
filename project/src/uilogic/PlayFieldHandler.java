package uilogic;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import file.elements.MapLayoutData;
import game.global.GameHandler;
import ui.elements.InteractiveGridPanel;

public class PlayFieldHandler extends InteractiveGridHandler{

    private double selectedTileDistance;
    private MapLayoutData currentMapLayoutData;

    public PlayFieldHandler(InteractiveGridPanel playField){
        super(playField, true);
    }

    public MapLayoutData getCurrentMapLayoutData() { return currentMapLayoutData; }
    public double getSelectedTileDistance() { return selectedTileDistance; }

    public void selectTile(Object o){            
        selectedTile = (GridPosition)o;

        try{
            var playerPosition = panel.getEntity(GameHandler.getInstance().getPlayer().getInstanceID()).getGridPosition();
            selectedTileDistance = GridPosition.calculateAbsoluteDistance(playerPosition, selectedTile);
        }
        catch(ArgumentNullException e){}
        catch(ElementNotFoundException e){}
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        panel.setMapLayout(data, gridButtonHandler, true);
        currentMapLayoutData = data;
    }

    public double getDistanceBetweenEntities(String srcID, String dstID) throws ArgumentNullException, ElementNotFoundException{
        if(srcID == null || dstID == null)
            throw new ArgumentNullException();

        var srcEntity = panel.getEntity(srcID);
        var dstEntity = panel.getEntity(dstID);
        double result = GridPosition.calculateAbsoluteDistance(srcEntity.getGridPosition(), dstEntity.getGridPosition());
        return result;
    }
}