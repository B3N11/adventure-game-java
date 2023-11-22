package uilogic;

import exception.general.ArgumentNullException;
import game.utility.delegates.GenericDelegate;
import ui.data.GridPosition;
import ui.elements.CharacterFrame;

public class CharacterFrameHandler {
    
    private CharacterFrame frame;
    private GridButtonHandler gridButtonHandler;
    private GridPosition selectedTile;

    public CharacterFrameHandler(){
        try{
            gridButtonHandler = new GridButtonHandler(new GenericDelegate() {
                public void run(Object o){ selectTile(o);}
            });            
        }catch(ArgumentNullException e){}
    }

    public GridButtonHandler getGridButtonHandler() { return gridButtonHandler; }

    public void setCharacterFrame(CharacterFrame frame) throws ArgumentNullException{
        if(frame == null)
            throw new ArgumentNullException();
        this.frame = frame;
    }

    public void selectTile(Object o){
        var tilePosition = (GridPosition)o;
        selectedTile = tilePosition;
    }
}