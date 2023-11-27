package game.behaviour.entities;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.behaviour.Item;
import game.behaviour.abstracts.Entity;

/**
 * This class represents an InventoryEntity in the game.
 * It extends the Entity class and includes an additional property: inventory.
 * 
 * The class contains the following fields:
 * - inventory: The inventory of the entity.
 * 
 * The class provides a constructor that initializes these fields and methods to add to the inventory and get the inventory.
 */
public class InventoryEntity extends Entity{

    protected transient Inventory inventory;

    /**
    * Constructor for the InventoryEntity class.
    * Initializes the health, movement, level, and inventory of the entity.
    * @param health The health of the entity.
    * @param movement The movement speed of the entity.
    * @param level The level of the entity.
    * @param removeConsumableFromInventoryWhenRanOut Whether to remove consumables from the inventory when they run out.
    * @throws InvalidArgumentException if the health, movement, or level is invalid.
    * @throws ArgumentNullException if the inventory is null.
    */
    public InventoryEntity(int health, int movement, int level, boolean removeConsumableFromInventoryWhenRanOut) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level);

        createInventory(removeConsumableFromInventoryWhenRanOut);
    }

    /**
     * Creates a new inventory for the entity.
     * @param removeWhenRanOut Whether to remove items from the inventory when they run out.
     */
    public void createInventory(boolean removeWhenRanOut){ inventory = new Inventory(removeWhenRanOut); }

    /**
     * Adds an item to the entity's inventory.
     * @param item The item to add.
     * @throws ArgumentNullException if the item is null.
     */
    public void addToInventory(Item item) throws ArgumentNullException{
        if(inventory == null)
            inventory = new Inventory(false);
        inventory.add(item);
    }

    public Inventory getInventory() { return inventory; }
}
