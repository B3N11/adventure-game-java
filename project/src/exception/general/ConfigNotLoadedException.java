package exception.general;

public class ConfigNotLoadedException extends Exception{
    public ConfigNotLoadedException(){
        super("The game configuration has not been loaded yet.");
    }
}
