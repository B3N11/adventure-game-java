package main.exception.dice;

public class InvalidDiceSideCountException extends Exception{
    public InvalidDiceSideCountException(){
        super("Given side count is invalid. Must be 1 or greater.");
    }
}
