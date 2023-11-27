package game.global.storage;

import game.behaviour.entities.items.Item;

/**
 * This class represents an ItemStorage in the game. It stores all the items in the game so that they can be reused. The key is the ID of the item.
 * It extends the Storage class with Item type.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the ItemStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class ItemStorage extends Storage<Item>{
    private static ItemStorage instance;

    private ItemStorage(){}

    public static ItemStorage getInstance(){
        if(instance == null)
            instance = new ItemStorage();
        return instance;
    }
}
