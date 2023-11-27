package main.exception.ui;

public class PlayfieldNotEmptyException extends Exception{
    public PlayfieldNotEmptyException(){
        super("The Playfield contains live entities, switching MapLayout is not permited.");
    }
}
