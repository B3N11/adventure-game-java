package file.elements;

import java.io.Serializable;

import ui.data.GridPosition;

/**
 * This class represents the data of an item on the map.
 * It includes the item's ID and its position on the grid.
 * 
 * The class contains the following fields:
 * - itemID: The ID of the item.
 * - position: The position of the item on the grid.
 */
public class ItemMapData implements Serializable{
    public String itemID;
    public GridPosition position;
}
