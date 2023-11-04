package game.behaviour.abstracts;

import game.enums.ItemType;

public abstract class Equipment extends Item{
    
    protected boolean equipped;

    public Equipment(){
        equipped = false;
        itemType = ItemType.EQUIPMENT;
    }

    public boolean isEquipped() { return equipped; }
    public void equip(boolean equip) { equipped = equip;  }
}