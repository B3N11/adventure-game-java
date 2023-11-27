package main.uilogic;

import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.file.elements.MapLayoutData;
import main.game.global.GameHandler;
import main.ui.elements.InteractiveGridPanel;

/**
 * This class handles the play field in the UI.
 * It extends the InteractiveGridHandler class and contains a MapLayoutData for the current map layout and a double for the distance of the selected tile.
 * 
 * The class contains the following fields:
 * - selectedTileDistance: The distance of the selected tile.
 * - currentMapLayoutData: The MapLayoutData for the current map layout.
 */
public class PlayFieldHandler extends InteractiveGridHandler{

    private double selectedTileDistance;
    private MapLayoutData currentMapLayoutData;

    /**
     * Constructor for the PlayFieldHandler class.
     * Initializes the PlayFieldHandler with an InteractiveGridPanel and sets the highlightButton to true.
     * @param playField The InteractiveGridPanel of the play field.
     * @throws ArgumentNullException if the InteractiveGridPanel is null.
     */
    public PlayFieldHandler(InteractiveGridPanel playField){
        super(playField, true);
    }

    /**
     * Gets the MapLayoutData for the current map layout.
     * @return The MapLayoutData for the current map layout.
     */
    public MapLayoutData getCurrentMapLayoutData() { return currentMapLayoutData; }
    
    /**
     * Gets the distance of the selected tile.
     * @return The distance of the selected tile.
     */
    public double getSelectedTileDistance() { return selectedTileDistance; }

    /**
     * Selects a tile on the play field and calculates the distance of the selected tile from the player.
     * @param o The object representing the grid position of the tile to select.
     */
    public void selectTile(Object o){            
        selectedTile = (GridPosition)o;

        try{
            var playerPosition = panel.getEntity(GameHandler.getInstance().getPlayer().getInstanceID()).getGridPosition();
            selectedTileDistance = GridPosition.calculateAbsoluteDistance(playerPosition, selectedTile);
        }
        catch(ArgumentNullException e){}
        catch(ElementNotFoundException e){}
    }
    
    /**
     * Sets the MapLayoutData for the current map layout and updates the map layout of the play field.
     * @param data The new MapLayoutData for the current map layout.
     * @throws Exception if there is an error setting the map layout.
     */
    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        panel.setMapLayout(data, gridButtonHandler, true);
        currentMapLayoutData = data;
    }

    /**
     * Gets the distance between two entities by their IDs.
     * @param srcID The ID of the source entity.
     * @param dstID The ID of the destination entity.
     * @return The distance between the source entity and the destination entity.
     * @throws ArgumentNullException if the source ID or destination ID is null.
     * @throws ElementNotFoundException if the source entity or destination entity with the specified ID is not found.
     */
    public double getDistanceBetweenEntities(String srcID, String dstID) throws ArgumentNullException, ElementNotFoundException{
        if(srcID == null || dstID == null)
            throw new ArgumentNullException();

        var srcEntity = panel.getEntity(srcID);
        var dstEntity = panel.getEntity(dstID);
        double result = GridPosition.calculateAbsoluteDistance(srcEntity.getGridPosition(), dstEntity.getGridPosition());
        return result;
    }
}