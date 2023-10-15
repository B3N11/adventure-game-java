package ui.handlers;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import ui.elements.GridEntityComponent;

public class GridEntityComponentHandler {
    
    private ArrayList<GridEntityComponent> entities;

    public GridEntityComponentHandler(){
        entities = new ArrayList<GridEntityComponent>();
    }

    public GridEntityComponent add(GridEntityComponent entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();

        entities.add(entity);

        return entity;
    }

    public GridEntityComponent getByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();

        for(var entity : entities)
            if(entity.getID().equals(id))
                return entity;
        throw new ElementNotFoundException();
    }
}
