package main.exception.general;

public class InvalidArgumentException extends Exception{
    public InvalidArgumentException(){
        super("The argument cannot be this value!");
    }
}
