package file.elements;

import java.io.Serializable;

import game.behaviour.Item;

/**
 * This class represents the saved data of an item.
 * It includes the item's object and the path to its icon file.
 * 
 * The class contains the following fields:
 * - item: The item object.
 * - iconFilePath: The path to the item's icon file.
 */
public class ItemSave implements Serializable{
    public Item item;
    public String iconFilePath;
}