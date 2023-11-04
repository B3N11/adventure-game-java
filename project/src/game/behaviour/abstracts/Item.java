package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import game.enums.ItemType;
import game.utility.general.Identifiable;

public abstract class Item extends Identifiable{
    protected String name;                         //The name of the equipment (NOT THE TYPE! Type is defined by the derived class)
    protected String description;                  //A brief introduction of the equipment
    protected ItemType itemType;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ItemType getItemType() { return itemType; }

    public Item setName(String name) throws ArgumentNullException{
        if(name == null)
            throw new ArgumentNullException();

        this.name = name;
        return this;
    }

    public Item setDescription(String description) throws ArgumentNullException{
        if(description == null)
            throw new ArgumentNullException();

        this.description = description;
        return this;
    }
}