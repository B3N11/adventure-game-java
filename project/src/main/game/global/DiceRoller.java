package main.game.global;

import main.exception.dice.DefaultDiceNotSetException;
import main.exception.dice.InvalidDiceSideCountException;
import main.game.utility.Dice;

/**
 * This class represents a DiceRoller in the game.
 * It is a singleton class that handles the rolling of dice.
 * 
 * The class contains the following fields:
 * - diceRoller: The singleton instance of the DiceRoller class.
 * - defaultDice: The default dice that is rolled on "rollDefault".
 * - delegateOnRoll: The delegate that is called when a dice is rolled.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class DiceRoller {

    private static DiceRoller diceRoller;

    private Dice defaultDice;

    private DiceRoller(){}

    public static DiceRoller getInstance() {
        if(diceRoller == null)
            diceRoller = new DiceRoller();

        return diceRoller;
    }

    public int getDefault() { return defaultDice.getSideCount(); }

    /**
     * Sets the default dice.
     * @param sides The number of sides on the default dice.
     * @throws InvalidDiceSideCountException if the number of sides is less than 1.
     */
    public void setDefault(int sides) throws InvalidDiceSideCountException{
        defaultDice = new Dice(sides);
    }

    /**
     * Rolls the default dice with a bonus.
     * @param bonus The bonus to add to the dice roll.
     * @return The result of the dice roll plus the bonus.
     * @throws DefaultDiceNotSetException if the default dice is not set.
     */
    public int rollDefault(int bonus) throws DefaultDiceNotSetException{
        if(defaultDice == null)
            throw new DefaultDiceNotSetException();
        
        return defaultDice.roll() + bonus;
    }

    /**
     * Rolls a dice with a specified number of sides, a specified number of times, with a bonus.
     * @param sides The number of sides on the dice.
     * @param rolls The number of times to roll the dice.
     * @param rollBonus The bonus to add to each dice roll.
     * @return The total result of all the dice rolls plus the bonuses.
     * @throws InvalidDiceSideCountException if the number of sides is less than 1.
     */
    public int rollDice(int sides, int rolls, int rollBonus) throws InvalidDiceSideCountException{
        var dice = new Dice(sides);

        int result = 0;
        for(int i = 0; i < rolls; i++)
            result += dice.roll() + rollBonus;
        
        return result;
    }
}
