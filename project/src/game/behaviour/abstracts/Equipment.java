package game.behaviour.abstracts;

import game.behaviour.entities.items.Item;
import game.enums.EquipmentType;
import game.enums.ItemType;

/**
 * This abstract class represents an Equipment in the game.
 * It extends the Item class and includes additional properties such as equipment type.
 * 
 * The class contains the following fields:
 * - equipmentType: The type of the equipment.
 * 
 * The class provides getter methods for these fields.
 */
public abstract class Equipment extends Item{
    
    protected EquipmentType equipmentType;

    protected Equipment(){
        itemType = ItemType.EQUIPMENT;
    }

    public EquipmentType getEquipmentType() { return equipmentType; }
}