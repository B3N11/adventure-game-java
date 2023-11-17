package file.elements;

import java.io.Serializable;
import java.util.ArrayList;

import game.behaviour.entities.Player;

public class PlayerProgressSave implements Serializable{
    
    public String modifiedEnemyFilePath;
    public String currentMapID;
    public String currentIconFile;
    
    public Player player;
    public String playerArmorID;
    public String playerWeaponID;
    public ArrayList<String> inventory;

    public PlayerProgressSave(){
        inventory = new ArrayList<String>();
    }
}