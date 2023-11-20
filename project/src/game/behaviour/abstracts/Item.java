package game.behaviour.abstracts;

import game.enums.ItemType;
import game.utility.general.Identity;
import ui.interfaces.IDisplayable;

public abstract class Item extends Identity implements IDisplayable{
    protected ItemType itemType;

    public ItemType getItemType() { return itemType; }
}