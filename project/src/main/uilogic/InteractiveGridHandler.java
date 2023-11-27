package main.uilogic;

import java.io.IOException;

import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.exception.general.InvalidArgumentException;
import main.exception.ui.ComponentAlreadyAtPositionException;
import main.game.utility.GenericDelegate;
import main.ui.elements.GridEntityComponent;
import main.ui.elements.InteractiveGridPanel;

/**
 * This class handles the interactive grid in the UI. It is used for placing, removing and handling entities on the grid.
 * It contains an InteractiveGridPanel, a GridButtonHandler, and a GridPosition for the selected tile.
 * 
 * The class contains the following fields:
 * - panel: The InteractiveGridPanel of the interactive grid.
 * - gridButtonHandler: The GridButtonHandler for handling grid button actions.
 * - selectedTile: The GridPosition of the selected tile.
 */
public class InteractiveGridHandler {
    protected InteractiveGridPanel panel;
    protected GridButtonHandler gridButtonHandler;

    protected GridPosition selectedTile;

    /**
     * Constructor for the InteractiveGridHandler class.
     * Initializes the InteractiveGridHandler with an InteractiveGridPanel and a boolean for whether to highlight the selected tile.
     * @param panel The InteractiveGridPanel of the interactive grid.
     * @param highlightButton The boolean for whether to highlight the selected tile.
     * @throws ArgumentNullException if the InteractiveGridPanel is null.
     */
    public InteractiveGridHandler(InteractiveGridPanel panel, boolean highlightButton){
        this.panel = panel;

        try{
            gridButtonHandler = new GridButtonHandler(new GenericDelegate() {
                public void run(Object o) { selectTile(o); }
            }, highlightButton);
        }catch(ArgumentNullException e){}
    }

    public InteractiveGridPanel getPlayField() { return panel; }
    public GridButtonHandler getGridButtonHandler() { return gridButtonHandler; }
    public GridPosition getSelectedTile() { return selectedTile; }

    /**
     * Sets the InteractiveGridPanel of the interactive grid.
     * @param panel The new InteractiveGridPanel of the interactive grid.
     * @throws ArgumentNullException if the new InteractiveGridPanel is null.
     */
    public void setPanel(InteractiveGridPanel panel) throws ArgumentNullException{
        if(panel == null)
            throw new ArgumentNullException();
        this.panel = panel;
    }

    /**
     * Selects a tile on the interactive grid.
     * @param o The object representing the grid position of the tile to select.
     */
    public void selectTile(Object o){            
        selectedTile = (GridPosition)o;
    }
    
    /**
     * Places an entity on the interactive grid.
     * @param id The ID of the entity.
     * @param position The grid position of the entity.
     * @param imagePath The image path of the entity.
     * @throws ArgumentNullException if the ID, grid position, or image path is null.
     * @throws InvalidArgumentException if the ID is empty, or the grid position or image path is invalid.
     * @throws ComponentAlreadyAtPositionException if there is already an entity at the grid position.
     * @throws IOException if there is an error reading the image.
     */
    public void placeEntity(String id, GridPosition position, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(id,
            panel.getComponentSize().getHorizontal(),
            panel.getComponentSize().getVertical(),
            position);
        component.setImage(imagePath);

        panel.addEntity(component);
    }

    /**
     * Removes an entity from the interactive grid.
     * @param id The ID of the entity.
     * @throws ArgumentNullException if the ID is null.
     * @throws ElementNotFoundException if the entity with the specified ID is not found.
     * @throws InvalidArgumentException if the ID is empty.
     */
    public void removeEntity(String id) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        if(id == null)
            throw new ArgumentNullException();
        
        panel.removeEntity(id, true);
    }

    /**
     * Replaces an entity on the interactive grid with a new grid position.
     * @param id The ID of the entity.
     * @param newPosition The new grid position of the entity.
     * @throws ArgumentNullException if the ID or new grid position is null.
     * @throws ElementNotFoundException if the entity with the specified ID is not found.
     * @throws InvalidArgumentException if the ID is empty, or the new grid position is invalid.
     * @throws ComponentAlreadyAtPositionException if there is already an entity at the new grid position.
     */
    public void replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        panel.replaceEntity(id, newPosition);
    }

    /**
     * Gets the grid position of an entity by its ID.
     * @param id The ID of the entity.
     * @return The grid position of the entity.
     * @throws ArgumentNullException if the ID is null.
     * @throws ElementNotFoundException if the entity with the specified ID is not found.
     */
    public GridPosition getEntityPositionByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();
        return panel.getEntity(id).getGridPosition();
    }

    /**
     * Gets the ID of an entity by its grid position.
     * @param position The grid position of the entity.
     * @return The ID of the entity.
     * @throws ArgumentNullException if the grid position is null.
     * @throws ElementNotFoundException if the entity at the specified grid position is not found.
     */
    public String getEntityIDByPosition(GridPosition position) throws ElementNotFoundException, ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
            
        return panel.getEntityByPosition(position);
    }
}
