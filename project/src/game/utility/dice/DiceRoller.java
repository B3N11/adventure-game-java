package game.utility.dice;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;
import exception.general.ArgumentNullException;
import game.utility.delegates.GenericDelegate;

//Singleton class used for storing and rolling dices
public class DiceRoller {

    //The singleton instance
    private static DiceRoller diceRoller;

    private Dice defaultDice;                       //The default dice that is rolled on "rollDefault"
    private GenericDelegate delegateOnRoll;

    //Private constructor for singleton pattern
    private DiceRoller(){}

    //Overriding ISingleton getInstance function to return the singleton instance
    public static DiceRoller getInstance() {
        if(diceRoller == null)
            diceRoller = new DiceRoller();

        return diceRoller;
    }

    public void setDelegate(GenericDelegate delegate) throws ArgumentNullException{
        if(delegate == null)
            throw new ArgumentNullException();
        this.delegateOnRoll = delegate;
    }

    //Set default dice
    public void setDefault(int sides) throws InvalidDiceSideCountException{
        defaultDice = new Dice(sides);
    }

    public int rollDefault(int bonus) throws DefaultDiceNotSetException{
        //Check if default dice is set
        if(defaultDice == null)
            throw new DefaultDiceNotSetException();
        
        int result = defaultDice.roll() + bonus;
        callDelegateWithRollResult(result);
        return result;
    }

    public int rollDice(int sides, int rolls, int rollBonus) throws InvalidDiceSideCountException{
        //Get dice
        var dice = new Dice(sides);

        int result = 0;
        for(int i = 0; i < rolls; i++)
            result += dice.roll() + rollBonus;
        
        callDelegateWithRollResult(result);
        return result;
    }

    private void callDelegateWithRollResult(int roll){
        if(delegateOnRoll != null)
            delegateOnRoll.run(roll);
    }
}
