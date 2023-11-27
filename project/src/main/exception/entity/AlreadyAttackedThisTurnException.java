package main.exception.entity;

public class AlreadyAttackedThisTurnException extends Exception{
    public AlreadyAttackedThisTurnException(){
        super("Entity already attacked in this turn.");
    }
}
