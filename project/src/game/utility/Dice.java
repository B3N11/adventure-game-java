package game.utility;

import java.util.concurrent.ThreadLocalRandom;

import exception.dice.InvalidDiceSideCountException;

/**
 * This class represents a dice with a variable number of sides.
 * It contains methods to set the number of sides, get the number of sides, and roll the dice.
 * 
 * The class contains the following fields:
 * - sideCount: The number of sides on the dice.
 */
public class Dice {

    private int sideCount;

    public Dice(int sides) throws InvalidDiceSideCountException{
        setSides(sides);
    }

    /**
     * Sets the number of sides on the dice.
     * @param sides The number of sides to set.
     * @throws InvalidDiceSideCountException if the number of sides is less than 1.
     */
    public void setSides(int sides) throws InvalidDiceSideCountException{
        if(sides < 1)
            throw new InvalidDiceSideCountException();

        sideCount = sides;
    }

    public int getSideCount(){
        return sideCount;
    }

    /**
     * Rolls the dice.
     * @return A random number between 1 and the number of sides on the dice.
     */
    public int roll(){
        return ThreadLocalRandom.current().nextInt(1, sideCount + 1);
    }
}