package game.global.storage;

import game.behaviour.entities.items.Item;

public class ItemStorage extends Storage<Item>{
    private static ItemStorage instance;

    private ItemStorage(){}

    public static ItemStorage getInstance(){
        if(instance == null)
            instance = new ItemStorage();
        return instance;
    }
}
