package uilogic;

import java.io.IOException;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import game.utility.GenericDelegate;
import ui.elements.GridEntityComponent;
import ui.elements.InteractiveGridPanel;

public class InteractiveGridHandler {
    protected InteractiveGridPanel panel;
    protected GridButtonHandler gridButtonHandler;

    protected GridPosition selectedTile;

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

    public void setPanel(InteractiveGridPanel panel) throws ArgumentNullException{
        if(panel == null)
            throw new ArgumentNullException();
        this.panel = panel;
    }

    public void selectTile(Object o){            
        selectedTile = (GridPosition)o;
    }

    public void placeEntity(String id, GridPosition position, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(id,
            panel.getComponentSize().getHorizontal(),
            panel.getComponentSize().getVertical(),
            position);
        component.setImage(imagePath);

        panel.addEntity(component);
    }

    public void removeEntity(String id) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        if(id == null)
            throw new ArgumentNullException();
        
        panel.removeEntity(id, true);
    }

    public void replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        panel.replaceEntity(id, newPosition);
    }

    public GridPosition getEntityPositionByID(String id) throws ArgumentNullException, ElementNotFoundException{
        if(id == null)
            throw new ArgumentNullException();
        return panel.getEntity(id).getGridPosition();
    }

    public String getEntityIDByPosition(GridPosition position) throws ElementNotFoundException, ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
            
        return panel.getEntityByPosition(position);
    }
}
