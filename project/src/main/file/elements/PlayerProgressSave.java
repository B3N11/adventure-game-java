package main.file.elements;

import java.io.Serializable;
import java.util.ArrayList;

import main.game.behaviour.entities.player.Player;
import main.game.utility.ModifiedEnemyData;
import main.uilogic.GridPosition;

public class PlayerProgressSave implements Serializable{

    public boolean modifiable;
    
    public String currentMapID;
    public String currentIconFile;
    
    public Player player;
    public GridPosition playerPosition;
    public String playerArmorID;
    public String playerWeaponID;
    public ArrayList<String> inventory;
    public ArrayList<ModifiedEnemyData> modifiedEnemies;

    public PlayerProgressSave(){
        inventory = new ArrayList<String>();
    }
}