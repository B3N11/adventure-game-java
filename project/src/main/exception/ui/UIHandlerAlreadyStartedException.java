package main.exception.ui;

public class UIHandlerAlreadyStartedException extends Exception{
    public UIHandlerAlreadyStartedException(){
        super("UIHandler was already started, can't start it again!");
    }
}
