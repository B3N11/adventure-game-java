package game.utility.dice;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;

//Singleton class used for storing and rolling dices
public class DiceRoller {

    //The singleton instance
    private static DiceRoller diceRoller;

    private Dice defaultDice;                       //The default dice that is rolled on "rollDefault"

    //Private constructor for singleton pattern
    private DiceRoller(){}

    //Overriding ISingleton getInstance function to return the singleton instance
    public static DiceRoller getInstance() {
        if(diceRoller == null)
            diceRoller = new DiceRoller();

        return diceRoller;
    }

    //Set default dice
    public void setDefault(int sides) throws InvalidDiceSideCountException{
        defaultDice = new Dice(sides);
    }

    public int rollDefault() throws DefaultDiceNotSetException{
        //Check if default dice is set
        if(defaultDice == null)
            throw new DefaultDiceNotSetException();
        
        return defaultDice.roll();
    }

    public int rollDice(int sides, int rolls, int rollBonus) throws InvalidDiceSideCountException{
        //Get dice
        var dice = new Dice(sides);

        int result = 0;
        for(int i = 0; i < rolls; i++)
            result += dice.roll() + rollBonus;
        
        return result;
    }
}
