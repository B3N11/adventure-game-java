package game.behaviour;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Equipment;
import game.behaviour.entities.items.Consumable;

/**
 * This class represents an InventoryMarker in the game. It is used to mark items in the inventory for removal.
 * It is a generic class that can hold an item of type T.
 * 
 * The class contains the following fields:
 * - item: The item of type T.
 * - markedForRemoval: A flag indicating whether the item is marked for removal.
 * 
 * The class provides a constructor that initializes these fields and methods to get the item, mark it for removal, and check if it is marked.
 */
public class InventoryMarker<T>{
    
    private T item;
    private boolean markedForRemoval;

    /**
     * Constructor for the InventoryMarker class.
     * Initializes the item and sets the markedForRemoval flag to false.
     * @param t The item to initialize.
     * @throws ArgumentNullException if the item is null.
     * @throws InvalidArgumentException if the item is not of type Consumable or Equipment.
     */
    public InventoryMarker(T t) throws ArgumentNullException, InvalidArgumentException{
        if(t == null)
            throw new ArgumentNullException();
        if(!(t instanceof Consumable) && !(t instanceof Equipment))
            throw new InvalidArgumentException();

        item = t;
    }

    public void mark(boolean mark) { markedForRemoval = mark; }
    public boolean isMarked() { return markedForRemoval; }
    public T getItem() { return item; }
}
