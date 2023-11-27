package game.behaviour.abstracts;

import game.behaviour.Item;
import game.enums.EquipmentType;
import game.enums.ItemType;

public abstract class Equipment extends Item{
    
    protected EquipmentType equipmentType;

    public Equipment(){
        itemType = ItemType.EQUIPMENT;
    }

    public EquipmentType getEquipmentType() { return equipmentType; }
}