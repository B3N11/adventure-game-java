package game.behaviour.abstracts;

import game.enums.ModifierType;
import game.utility.general.Identifiable;

public abstract class Consumable extends Identifiable{
    
    private double modifier;
    private ModifierType type;

    public double use() { return modifier;}
    public ModifierType getType() { return type; }
}
