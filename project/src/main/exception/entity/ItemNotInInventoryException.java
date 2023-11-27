package main.exception.entity;

public class ItemNotInInventoryException extends Exception{
    public ItemNotInInventoryException(){
        super("Item is not in inventory.");
    }
}
