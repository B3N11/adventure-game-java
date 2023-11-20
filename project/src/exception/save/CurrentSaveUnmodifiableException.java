package exception.save;

public class CurrentSaveUnmodifiableException extends Exception{
    public CurrentSaveUnmodifiableException(){
        super("The currently used save cannot be modified or overwritten.");
    }
}
