package exception.general;

//Thrown when function argument was null
public class ArgumentNullException extends Exception{
    public ArgumentNullException(){
        super("Function argument cannot be NULL.");
    }
}