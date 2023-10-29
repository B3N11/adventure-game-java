package game.behaviour.abstracts;

import exception.general.InvalidArgumentException;
import game.enums.ModifierType;

public abstract class Consumable extends Event{
    
    private double modifier;
    private int charges;
    private boolean toggled;
    private ModifierType type;

    public Consumable(ModifierType type, int amount){
        toggled = false;
        this.type = type;
        charges = amount;
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
            triggerEvent();

        return charges;
    }
}