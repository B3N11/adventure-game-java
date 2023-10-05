package exception.general;

public class ElementNotFoundException extends Exception{
    public ElementNotFoundException(){
        super("Element with specified parameter(s) was not found in the collection.");
    }
}
