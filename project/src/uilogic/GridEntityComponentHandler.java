package uilogic;

import java.util.HashMap;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import ui.data.GridPosition;
import ui.elements.GridEntityComponent;

public class GridEntityComponentHandler {
    
    private HashMap<String, GridEntityComponent> entities;

    public GridEntityComponentHandler(){
        entities = new HashMap<String, GridEntityComponent>();
    }

    public boolean isEmpty(){ return entities.isEmpty(); }

    public void clear(){
        entities.clear();
    }

    public GridEntityComponent add(GridEntityComponent entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();

        entities.put(entity.getID(), entity);

        return entity;
    }

    public GridEntityComponent getByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();

        var result = entities.get(id);

        if(result != null)
            return result;

        throw new ElementNotFoundException();
    }

    public GridEntityComponent getByPosition(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        if(position == null)
            throw new ArgumentNullException();
        
        for(var entity : entities.entrySet())
            if(entity.getValue().getGridPosition().equals(position))
                return entity.getValue();
        throw new ElementNotFoundException();
    }

    public GridEntityComponent remove(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();

        var entity = getByID(id);
        return remove(entity);
    }

    public GridEntityComponent remove(GridEntityComponent entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();
        
        entities.remove(entity.getID());        
        return entity;
    }
}
