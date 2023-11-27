package main.exception.dice;

public class DefaultDiceNotSetException extends Exception{
    public DefaultDiceNotSetException(){
        super("Default dice is not set. First call the setDefault() method!");
    }
}
