package file.elements;

import java.io.Serializable;
import java.util.ArrayList;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.Item;
import game.behaviour.entities.Player;

public class PlayerProgressSave implements Serializable{
    
    private String modifiedEnemyFilePath;
    private String currentMapID;
    
    private Player player;
    private String playerArmorID;
    private String playerWeaponID;
    private ArrayList<String> inventory;

    public PlayerProgressSave(Player player) throws ArgumentNullException{
        setPlayer(player);
        inventory = new ArrayList<String>();
    }

    public Player getPlayer() { return player; }
    public String getModifiedEnemyFilePath() { return modifiedEnemyFilePath; }
    public String getCurrentMapID() { return currentMapID; }
    public String getPlayerArmorID() { return playerArmorID; }
    public String getPlayerWeaponID() { return playerWeaponID; }
    public ArrayList<String> getInventory() { return inventory; }

    public PlayerProgressSave setPlayer(Player player) throws ArgumentNullException{
        if(player == null)
            throw new ArgumentNullException();

        this.player = player;
        return this;
    }

    public PlayerProgressSave addToInventory(Item item) throws ArgumentNullException{
        if(item == null)
            throw new ArgumentNullException();
        
        inventory.add(item.getID());
        
        return this;
    }

    public PlayerProgressSave setPlayerArmorID(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        playerArmorID = id;
        return this;
    }

    public PlayerProgressSave setPlayerWeaponID(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        playerWeaponID = id;
        return this;
    }

    public PlayerProgressSave setModifiedEnemyFilePath(String filePath) throws ArgumentNullException{
        if(filePath == null)
            throw new ArgumentNullException();

        this.modifiedEnemyFilePath = filePath;
        return this;
    }

    public PlayerProgressSave setCurrentMapID(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        this.currentMapID = id;
        return this;
    }
}
