package main.exception.general;

public class ElementAlreadyInCollectionException extends Exception{
    public ElementAlreadyInCollectionException(){
        super("Element is already in the collection.");
    }
}
