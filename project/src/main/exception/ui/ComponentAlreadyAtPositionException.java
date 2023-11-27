package main.exception.ui;

public class ComponentAlreadyAtPositionException extends Exception{
    public ComponentAlreadyAtPositionException(){
        super("There is already a component at the specified position.");
    }
}
