package game.behaviour.entities.items;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.ItemType;
import game.enums.ModifierType;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;

/**
 * This class represents a Consumable item in the game.
 * It extends the Item class.
 * 
 * The class contains the following fields:
 * - modifier: The modifier of the consumable.
 * - charges: The number of charges of the consumable.
 * - toggled: A flag indicating whether the consumable is toggled on.
 * - type: The type of the consumable.
 * - onOutOfChargesEvent: An event that is triggered when the consumable is out of charges.
 * 
 * The class provides a constructor that initializes these fields and methods to get the charges, type, and toggled status, and to set the charges.
 */
public class Consumable extends Item{
    
    private double modifier;
    private int charges;
    private boolean toggled;
    private ModifierType type;

    private transient Event onOutOfChargesEvent;

    /**
     * Constructor for the Consumable class.
     * Initializes the id, type, and charges of the consumable and sets the itemType to CONSUMABLE.
     * @param id The id of the consumable.
     * @param type The type of the consumable.
     * @param amount The number of charges of the consumable.
     * @throws ArgumentNullException if the id is null.
     */
    public Consumable(String id, ModifierType type, int amount) throws ArgumentNullException{
        setID(id);
        toggled = false;
        this.type = type;
        charges = amount;
        onOutOfChargesEvent = new Event(new EventArgument<Consumable>().setArgument(this));
        itemType = ItemType.CONSUMABLE;
    }

    public int getCharges() { return charges; }
    public ModifierType getType() { return type; }
    public boolean isOn() { return toggled;}

    public Consumable setCharges(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();

        charges = amount;
        return this;
    }

    public void addCharges(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();
        
        charges += amount;
    }

    /**
     * Toggles the consumable on or off.
     * @param on True to toggle the consumable on, false to toggle it off.
     */
    public void toggle(boolean on) {
        toggled = on;
    }

    /**
     * Uses the consumable.
     * If the consumable is toggled on, it decreases the charges and returns the modifier.
     * @return The modifier of the consumable if it is toggled on, 0 otherwise.
     * @throws Exception if an error occurs during the use of the consumable.
     */
    public double use() throws Exception{
        if(!toggled)
            return 0;
        
        decreaseCharges();
        return modifier;
    }
        
    /**
     * Decreases the charges of the consumable by 1.
     * If the charges are less than 0, it sets the charges to 0.
     * If the charges are 0, it triggers the onOutOfChargesEvent.
     * @return The number of charges of the consumable.
     * @throws Exception if an error occurs during the decrease of the charges.
     */
    public int decreaseCharges() throws Exception{
        charges--;

        if(charges < 0)
            charges = 0;

        if(charges == 0)
            onOutOfChargesEvent.triggerEvent();

        return charges;
    }

    /**
     * Adds an event listener to the onOutOfChargesEvent.
     * @param listener The event listener to add.
     * @throws ArgumentNullException if the listener is null.
     */
    public void addEventListener(IEventListener listener) throws ArgumentNullException{
        onOutOfChargesEvent.addEventListener(listener);
    }
}