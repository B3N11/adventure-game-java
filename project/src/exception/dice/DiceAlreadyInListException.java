package exception.dice;

public class DiceAlreadyInListException extends Exception{
    public DiceAlreadyInListException(){
        super("Dice with similar side count already exists in the list.");
    }
}
