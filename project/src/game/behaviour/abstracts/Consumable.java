package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.interfaces.IEventListener;
import game.enums.ModifierType;
import game.utility.general.Identifiable;

public abstract class Consumable extends Identifiable{
    
    private double modifier;
    private int charges;
    private boolean toggled;
    private ModifierType type;

    private Event onOutOfChargesEvent;

    public Consumable(String id, ModifierType type, int amount) throws ArgumentNullException{
        setID(id);
        toggled = false;
        this.type = type;
        charges = amount;
        onOutOfChargesEvent = new Event(this);
    }

    public int getCharges() { return charges; }
    public ModifierType getType() { return type; }
    public boolean isOn() { return toggled;}

    public void setCharges(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();

        charges = amount;
    }

    public void addCharges(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();
        
        charges += amount;
    }

    public void toggle(boolean on) {
        toggled = on;
    }

    public double use() throws Exception{
        if(!toggled)
            return 0;
        
        decreaseCharges();
        return modifier;
    }

    public int decreaseCharges() throws Exception{
        charges--;

        if(charges < 0)
            charges = 0;

        if(charges == 0)
            onOutOfChargesEvent.triggerEvent();

        return charges;
    }

    public void addEventListener(IEventListener listener) throws ArgumentNullException{
        onOutOfChargesEvent.addEventListener(listener);
    }
}