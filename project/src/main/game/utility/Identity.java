package main.game.utility;

import main.exception.general.ArgumentNullException;

/**
 * This abstract class represents an identifiable object in the game with a name and description.
 * It extends the Identifiable class and contains methods to set and get the name and description of the object.
 * 
 * The class contains the following fields:
 * - name: The name of the game object.
 * - description: The description of the game object.
 */
public abstract class Identity extends Identifiable{
    protected String name;
    protected String description;

    public String getName() { return name; }
    public String getDescription() { return description; }

    /**
     * Sets the name of the object.
     * @param name The new name to set.
     * @return The object itself, for chaining.
     * @throws ArgumentNullException if the new name is null.
     */
    public Identity setName(String name) throws ArgumentNullException{
        if(name == null)
            throw new ArgumentNullException();

        this.name = name;
        return this;
    }

    /**
     * Sets the description of the object.
     * @param description The new description to set.
     * @return The object itself, for chaining.
     * @throws ArgumentNullException if the new description is null.
     */
    public Identity setDescription(String description) throws ArgumentNullException{
        if(description == null)
            throw new ArgumentNullException();

        this.description = description;
        return this;
    }
}
