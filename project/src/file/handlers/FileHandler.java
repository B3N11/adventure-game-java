package file.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.ElementAlreadyInCollectionException;
import file.FileIOUtil;
import file.elements.PlayerProgressSave;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Item;
import game.behaviour.abstracts.Weapon;
import game.enums.ItemType;
import game.global.GameHandler;
import game.global.storage.ItemStorage;

public class FileHandler {
    
    private boolean started;

    private FileIOUtil fileIOUtil;

    private String itemFolderFilePath;
    private String enemyTypeFolderFilePath;

    private static FileHandler instance;

    private FileHandler(){
        started = false;
        fileIOUtil = new FileIOUtil();
    }

    public static FileHandler getInstance() {
        if(instance == null)
            instance = new FileHandler();
        return instance;
    }

    public boolean isStarted() { return started; }

    public void startHandler(String loadUpFilePath) throws ArgumentNullException, FileNotFoundException{
        if(loadUpFilePath == null)
            throw new ArgumentNullException();

        String content = fileIOUtil.readFile(loadUpFilePath);
        runHandler(content);
    }

    public void runHandler(String loadUpFileContent) throws ArgumentNullException{
        if(loadUpFileContent == null)
            throw new ArgumentNullException();
        
        var lines = loadUpFileContent.split("\n");

        for(var line : lines)
            setFolderPaths(line);
        
        started = true;
    }

    private void setFolderPaths(String fileLine) throws ArgumentNullException{
        if(fileLine == null)
            throw new ArgumentNullException();

        var parts = fileLine.split(":");

        switch (parts[0]) {
            case "items":
                itemFolderFilePath = parts[1];
                break;

            case "enemies":
                enemyTypeFolderFilePath = parts[1];
                break;
        
            default:
                return;
        }
    }

    private void loadPlayerProgressSave(String filePath) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException, ElementAlreadyInCollectionException, ItemNotInInventoryException{
        if(filePath == null)
            throw new ArgumentNullException();

        var playerProgress = (PlayerProgressSave)fileIOUtil.readObjectFromFile(filePath);
        var player = playerProgress.getPlayer();

        for(var item : playerProgress.getInventory()){
            var itemObject = loadItem(item);

            if(itemObject.getItemType() == ItemType.EQUIPMENT)
                player.addToInventory((Equipment)itemObject);
            if(itemObject.getItemType() == ItemType.CONSUMABLE)
                player.addToInventory((Consumable)itemObject);
        }

        var armor = (Armor)loadItem(playerProgress.getPlayerArmorID());
        var weapon = (Weapon)loadItem(playerProgress.getPlayerWeaponID());
        player.equip(armor);
        player.equip(weapon);
        GameHandler.getInstance().setSessionPlayer(player);
        loadCurrentMap(playerProgress.getCurrentMapID());
    }

    private void loadCurrentMap(String id){
        
    }

    private Item loadItem(String id) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException, ElementAlreadyInCollectionException{
        if(id == null)
            throw new ArgumentNullException();
        
        var result = (Item)ItemStorage.getInstance().get(id);
        if(result != null)
            return result;

        String fileName = id + ".txt";
        result = (Item)fileIOUtil.readObjectFromFile(new File(itemFolderFilePath, fileName));

        //Add to storage for later use
        ItemStorage.getInstance().add(result.getID(), result);

        return result;
    }
}
