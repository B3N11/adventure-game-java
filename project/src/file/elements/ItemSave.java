package file.elements;

import java.io.Serializable;

import game.behaviour.abstracts.Item;

public class ItemSave implements Serializable{
    public Item item;
    public String iconFilePath;
}
