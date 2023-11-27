package game.behaviour.entities.items;

import game.enums.ItemType;
import game.utility.IDisplayable;
import game.utility.Identity;

/**
 * This class represents an Item in the game.
 * It extends the Identity class and implements the IDisplayable interface.
 * 
 * The class contains the following field:
 * - itemType: The type of the item.
 * 
 * The class provides a constructor that initializes the itemType of the item and methods to get the itemType, display info, and statistics.
 */
public class Item extends Identity implements IDisplayable{
    protected ItemType itemType;
    
    /**
     * Constructor for the Item class.
     * Initializes the itemType of the item to SIMPLE.
     */
    public Item(){
        itemType = ItemType.SIMPLE;
    }

    public ItemType getItemType() { return itemType; }

    @Override
    public String getDisplayInfo() {
        return getDescription();
    }

    @Override
    public String getStatistics(int bearerLevel) {
        return "This item has no displayable statistics.";
    }
}