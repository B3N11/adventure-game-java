package file.elements;

import java.io.Serializable;

import game.behaviour.Item;

public class ItemSave implements Serializable{
    public Item item;
    public String iconFilePath;
}