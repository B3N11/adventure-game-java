package game.utility.general;

import exception.general.ArgumentNullException;

public abstract class Identifiable {

    protected String id;            //The ID of the game object

    protected final void setID(String newID) throws ArgumentNullException{
        //ID cannot be NULL
        if(newID == null)
            throw new ArgumentNullException();
        
        id = newID;
    }

    public final String getID(){
        return id;
    }
}
