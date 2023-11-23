package game.behaviour.abstracts;

import game.behaviour.Item;
import game.enums.EquipmentType;
import game.enums.ItemType;

public abstract class Equipment extends Item{
    
    protected boolean equipped;
    protected EquipmentType equipmentType;

    public Equipment(){
        equipped = false;
        itemType = ItemType.EQUIPMENT;
    }

    public boolean isEquipped() { return equipped; }
    public void equip(boolean equip) { equipped = equip;  }
    public EquipmentType getEquipmentType() { return equipmentType; }
}