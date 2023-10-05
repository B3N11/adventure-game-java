package game.utility.dice;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.DiceAlreadyInListException;
import exception.dice.InvalidDiceSideCountException;
//Own packages
import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;

//Java packages
import java.util.ArrayList;


//Singleton class used for storing and rolling dices
public class DiceRoller {

    //The singleton instance
    private static DiceRoller diceRoller;

    private ArrayList<Dice> diceList;               //The list storing for dices
    private Dice defaultDice;                       //The default dice that is rolled on "rollDefault"

    //#region SINGLETON PATTERN IMPLEMENTATION

    //Private constructor for singleton pattern
    private DiceRoller(){
        diceList = new ArrayList<Dice>();
    }

    //Overriding ISingleton getInstance function to return the singleton instance
    public static DiceRoller getInstance() {
        if(diceRoller == null)
            diceRoller = new DiceRoller();

        return diceRoller;
    }
    //#endregion

    //#region DICE LIST MANAGEMENT

    //Set default dice
    public void setDefault(int sides){
        defaultDice = new Dice(sides);
    }

    //Add a new dice to the list. Argument cannot be NULL.
    public void addDice(int sides) throws DiceAlreadyInListException, InvalidDiceSideCountException{        
        //Check side validity
        if(sides < 1)
            throw new InvalidDiceSideCountException();
        
        //Check if dice already exists. If not, move on
        try{
            getDice(sides);
            throw new DiceAlreadyInListException();
        }catch(ElementNotFoundException e){}

        diceList.add(new Dice(sides));
    }

    //Creates a new Dice for each element of the input array with sideCount matching the elements in array. Adds new Dices to the list.
    //if createNew is true, than a new list will be created, discarding the last one
    public void createDiceListFromArray(int[] sides, boolean createNew) throws ArgumentNullException {
        //Cant add null to dice list
        if(sides == null)
            throw new ArgumentNullException();

        //If createNew is true, than clear the dice list
        if(createNew)
            diceList.clear();

        //Add new dices with sides from input array
        int length = sides.length;
        for(int i = 0; i < length; i++){
            try{
                addDice(sides[i]);
            }catch(DiceAlreadyInListException e){
            }catch(InvalidDiceSideCountException e){}
        }            
    }

    public Dice getDice(int sides) throws ElementNotFoundException{
        //Search for the Dice based on sideCount
        for(var dice : diceList)
            if(dice.getSideCount() == sides)
                return dice;
        
        throw new ElementNotFoundException();
    }

    //Clear the dice list
    public void clearDiceList(){
        diceList.clear();
    }
    //#endregion

    //#region DICE ROLLING

    public int rollDefault() throws DefaultDiceNotSetException{
        //Check if default dice is set
        if(defaultDice == null)
            throw new DefaultDiceNotSetException();
        
        return defaultDice.roll();
    }

    public int rollDice(int sides, int rolls, int rollBonus) throws ElementNotFoundException{
        //Get dice
        var dice = getDice(sides);

        int result = 0;
        for(int i = 0; i < rolls; i++)
            result += dice.roll() + rollBonus;
        
        return result;
    }
    //#endregion
}
