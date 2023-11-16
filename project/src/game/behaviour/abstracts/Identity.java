package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import game.utility.general.Identifiable;

public abstract class Identity extends Identifiable{
    protected String name;
    protected String description;

    public String getName() { return name; }
    public String getDescription() { return description; }

    public Identity setName(String name) throws ArgumentNullException{
        if(name == null)
            throw new ArgumentNullException();

        this.name = name;
        return this;
    }

    public Identity setDescription(String description) throws ArgumentNullException{
        if(description == null)
            throw new ArgumentNullException();

        this.description = description;
        return this;
    }
}
