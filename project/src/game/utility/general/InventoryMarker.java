package game.utility.general;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Consumable;
import game.behaviour.abstracts.Equipment;

public class InventoryMarker<T>{
    
    private T item;
    private boolean markedForRemoval;

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
