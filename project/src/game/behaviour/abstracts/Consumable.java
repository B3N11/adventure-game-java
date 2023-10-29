package game.behaviour.abstracts;

import exception.general.InvalidArgumentException;
import game.enums.ModifierType;

public abstract class Consumable extends Event{
    
    private double modifier;
    private ModifierType type;
    private int charges;

    public int getCharges() { return charges; }
    public ModifierType getType() { return type; }

    public double use() throws InvalidArgumentException{
        triggerEvent();
        return modifier;
    }
}
