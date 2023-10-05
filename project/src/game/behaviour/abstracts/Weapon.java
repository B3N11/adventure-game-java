package game.behaviour.abstracts;

import exception.dice.DefaultDiceNotSetException;
import exception.general.ElementNotFoundException;
import game.utility.dice.DiceRoller;

public abstract class Weapon {
    
    private int baseAttackModifier;              //The bonus value that is added to the default attack roll
    private int damageDice;                      //Represents the sideCount of the damage dice it rolls with
    private int diceCount;                       //The number of dice that is rolled for damage
    private int damageModifier;                  //The bonus value that is added to the rolled damage

    private double range;

    protected Weapon(){

    }

    //Checks if the weapon can attack to that distance
    public boolean checkRange(int distance){
        return range >= distance;
    }

    //Does an attack roll with default dice and returns its value with attackModifier added to it
    public boolean attack(int armorClass) throws DefaultDiceNotSetException{
        var roller = DiceRoller.getInstance();

        //Check if the rolled value succeeds armorClass
        boolean result = (roller.rollDefault() + baseAttackModifier) >= armorClass;
        return result;
    }

    public int damage() throws ElementNotFoundException{
        var roller = DiceRoller.getInstance();

        return roller.rollDice(damageDice, diceCount, 0) + damageModifier;
    }
}
