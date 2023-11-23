package game.behaviour;

import game.enums.ItemType;
import game.utility.general.Identity;
import ui.interfaces.IDisplayable;

public class Item extends Identity implements IDisplayable{
    protected ItemType itemType;

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