package uilogic;

import java.io.IOException;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import game.behaviour.interfaces.IInteractiveEntity;
import game.utility.dataclass.MapLayoutData;
import game.utility.delegates.GenericDelegate;
import ui.data.GridPosition;
import ui.elements.GridEntityComponent;
import ui.elements.PlayfieldPanel;

public class PlayFieldHandler {
    
    private PlayfieldPanel playField;
    private GridButtonHandler gridButtonHandler;

    private GridPosition selectedTile;

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

    public void setPlayField(PlayfieldPanel playField) throws ArgumentNullException{
        if(playField == null)
            throw new ArgumentNullException();
        this.playField = playField;
    }

    public void selectTile(Object o){            
        var gridPosition = (GridPosition)o;

        selectedTile = gridPosition;
        System.out.println(selectedTile.getX() + " ; " + selectedTile.getY());
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        playField.setMapLayout(data, gridButtonHandler, true);
    }

    public void placeEntity(IInteractiveEntity entity, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(entity.getInstanceID(),
            playField.getComponentSize().getHorizontal(),
            playField.getComponentSize().getVertical(),
            entity.getPosition());

        component.setImage(imagePath);

        playField.addEntity(component);
    }
}