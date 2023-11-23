package game.behaviour.entities;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Consumable;
import game.behaviour.Inventory;
import game.behaviour.abstracts.Entity;
import game.behaviour.abstracts.Equipment;

public class InventoryEntity extends Entity{

    transient protected Inventory inventory;

    public InventoryEntity(int health, int movement, int level, boolean removeConsumableFromInventoryWhenRanOut) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level);

        createInventory(removeConsumableFromInventoryWhenRanOut);
    }

    public void createInventory(boolean removeWhenRanOut){ inventory = new Inventory(removeWhenRanOut); }

    public void addToInventory(Equipment equipment) throws ArgumentNullException{
        if(inventory == null)
            inventory = new Inventory(false);
        inventory.add(equipment);
    }

    public void addToInventory(Consumable consumable) throws ArgumentNullException{
        if(inventory == null)
            inventory = new Inventory(false);
        inventory.add(consumable);
    }

    public Inventory getInventory() { return inventory; }
}
