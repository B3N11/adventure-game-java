package game.utility;

import java.io.Serializable;

import exception.general.ArgumentNullException;

/**
 * This abstract class represents an identifiable object in the game.
 * It contains methods to set and get the ID of the object.
 * 
 * The class contains the following fields:
 * - id: The ID of the game object.
 */
public abstract class Identifiable implements Serializable{

    protected String id;            //The ID of the game object

    /**
     * Sets the ID of the object.
     * @param newID The new ID to set.
     * @return The object itself, for chaining.
     * @throws ArgumentNullException if the new ID is null.
     */
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