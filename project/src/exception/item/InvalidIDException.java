package exception.item;

public class InvalidIDException extends Exception{
    public InvalidIDException(){
        super("The given ID has no corresponding item to it.");
    }
}
