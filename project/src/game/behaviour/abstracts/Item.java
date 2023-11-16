package game.behaviour.abstracts;

import game.enums.ItemType;
import game.utility.general.Identity;

public abstract class Item extends Identity{
    protected ItemType itemType;

    public ItemType getItemType() { return itemType; }
}