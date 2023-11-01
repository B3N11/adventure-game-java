package game.behaviour.abstracts;

import game.utility.general.Identifiable;

public abstract class Equipment extends Identifiable{
    protected String name;                         //The name of the equipment (NOT THE TYPE! Type is defined by the derived class)
    protected String description;                  //A brief introduction of the equipment
}
