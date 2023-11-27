package main.uilogic;

import java.util.HashMap;

import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.ui.elements.GridEntityComponent;

/**
 * This class handles the grid entity components in the UI. It stores the grid entity components in a HashMap. The keys are the IDs of the grid entity components.
 * It contains a HashMap of GridEntityComponents, with their IDs as keys.
 * 
 * The class contains the following field:
 * - entities: The HashMap of GridEntityComponents.
 */
public class GridEntityComponentHandler {
    
    private HashMap<String, GridEntityComponent> entities;

    /**
     * Constructor for the GridEntityComponentHandler class.
     * Initializes the HashMap of GridEntityComponents.
     */
    public GridEntityComponentHandler(){
        entities = new HashMap<String, GridEntityComponent>();
    }

    /**
     * Checks if the HashMap of GridEntityComponents is empty.
     * @return A boolean indicating whether the HashMap of GridEntityComponents is empty.
     */
    public boolean isEmpty(){ return entities.isEmpty(); }

    /**
     * Clears the HashMap of GridEntityComponents.
     */
    public void clear(){
        entities.clear();
    }

    /**
     * Adds a GridEntityComponent to the HashMap of GridEntityComponents.
     * @param entity The GridEntityComponent to add.
     * @return The added GridEntityComponent.
     * @throws ArgumentNullException if the GridEntityComponent is null.
     */
    public GridEntityComponent add(GridEntityComponent entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();

        entities.put(entity.getID(), entity);

        return entity;
    }

    /**
     * Gets a GridEntityComponent from the HashMap of GridEntityComponents by its ID.
     * @param id The ID of the GridEntityComponent.
     * @return The GridEntityComponent with the specified ID.
     * @throws ArgumentNullException if the ID is null.
     * @throws ElementNotFoundException if the GridEntityComponent with the specified ID is not found.
     */
    public GridEntityComponent getByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();

        var result = entities.get(id);

        if(result != null)
            return result;

        throw new ElementNotFoundException();
    }

    /**
     * Gets a GridEntityComponent from the HashMap of GridEntityComponents by its position.
     * @param position The position of the GridEntityComponent.
     * @return The GridEntityComponent with the specified position.
     * @throws ArgumentNullException if the position is null.
     * @throws ElementNotFoundException if the GridEntityComponent with the specified position is not found.
     */
    public GridEntityComponent getByPosition(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        if(position == null)
            throw new ArgumentNullException();
        
        for(var entity : entities.entrySet())
            if(entity.getValue().getGridPosition().equals(position))
                return entity.getValue();
        throw new ElementNotFoundException();
    }

    /**
     * Removes a GridEntityComponent from the HashMap of GridEntityComponents by its ID.
     * @param id The ID of the GridEntityComponent.
     * @return The removed GridEntityComponent.
     * @throws ArgumentNullException if the ID is null.
     * @throws ElementNotFoundException if the GridEntityComponent with the specified ID is not found.
     */
    public GridEntityComponent remove(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();

        var entity = getByID(id);
        return remove(entity);
    }

    /**
     * Removes a GridEntityComponent from the HashMap of GridEntityComponents.
     * @param entity The GridEntityComponent to remove.
     * @return The removed GridEntityComponent.
     * @throws ArgumentNullException if the GridEntityComponent is null.
     */
    public GridEntityComponent remove(GridEntityComponent entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();
        
        entities.remove(entity.getID());        
        return entity;
    }
}
