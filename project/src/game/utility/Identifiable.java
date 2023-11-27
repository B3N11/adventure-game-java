package game.utility;

import java.io.Serializable;

import exception.general.ArgumentNullException;

public abstract class Identifiable implements Serializable{

    protected String id;            //The ID of the game object

    public final Identifiable setID(String newID) throws ArgumentNullException{
        //ID cannot be NULL
        if(newID == null)
            throw new ArgumentNullException();
        
        id = newID;
        return this;
    }

    public final String getID(){
        return id;
    }
}