package file.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.ElementAlreadyInCollectionException;
import exception.general.InvalidArgumentException;
import file.FileIOUtil;
import file.elements.EnemyTypeSave;
import file.elements.PlayerProgressSave;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Item;
import game.behaviour.abstracts.Weapon;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyType;
import game.enums.ItemType;
import game.global.GameHandler;
import game.global.storage.ActiveEnemyStorage;
import game.global.storage.EnemyTypeStorage;
import game.global.storage.ItemStorage;
import game.global.storage.ModifiedEnemyStorage;
import uilogic.MapLayoutData;

public class FileHandler {
    
    private boolean started;

    private FileIOUtil fileIOUtil;

    private String itemFolderFilePath;
    private String enemyTypeFolderFilePath;
    private String mapLayoutFolderFilePath;
    private String baseInfoFolderFilePath;

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
             
            case "base":
                baseInfoFolderFilePath = parts[0];
                break;

            case "items":
                itemFolderFilePath = parts[1];
                break;

            case "enemies":
                enemyTypeFolderFilePath = parts[2];
                break;

            case "mapdata":
                mapLayoutFolderFilePath = parts[3];
                break;

            default:
                return;
        }
    }

    private void loadPlayerProgressSave(String filePath) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException, ElementAlreadyInCollectionException, ItemNotInInventoryException, InvalidArgumentException{
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

        //TODO: Implement file format exception

        //TODO: Implement modified enemy load

        var armor = (Armor)loadItem(playerProgress.getPlayerArmorID());
        var weapon = (Weapon)loadItem(playerProgress.getPlayerWeaponID());
        player.equip(armor);
        player.equip(weapon);
        GameHandler.getInstance().setSessionPlayer(player);
        loadCurrentMap(playerProgress.getCurrentMapID());
    }

    private void loadModifiedEnemies(){
        
    }

    public void loadCurrentMap(String id) throws FileNotFoundException, ClassNotFoundException, ArgumentNullException, IOException, ItemNotInInventoryException, ElementAlreadyInCollectionException, InvalidArgumentException{
        String fileName = id + ".txt";
        var mapData = (MapLayoutData)fileIOUtil.readObjectFromFile(new File(mapLayoutFolderFilePath, fileName));

        for(var enemyData : mapData.getEnemies()){

            var enemyType = loadEnemyType(enemyData.getAssetID());
            var enemy = new Enemy(enemyData.getInstanceID(), enemyType);

            ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);

            if(!ModifiedEnemyStorage.getInstance().contains(enemyData.getInstanceID()))
                continue;
            
            var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getID());
            enemy.setPosition(modifiedData.getPosition());
            enemy.setCurrentHealth(modifiedData.getHealth());

            //TODO: Implement looted state
        }
    }

    private EnemyType loadEnemyType(String id) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException, ItemNotInInventoryException, ElementAlreadyInCollectionException{
        if(id == null)
            throw new ArgumentNullException();

        var result = EnemyTypeStorage.getInstance().get(id);
        if(result != null)
            return result;

        String fileName = id + ".txt";
        var save = (EnemyTypeSave)fileIOUtil.readObjectFromFile(new File(enemyTypeFolderFilePath, fileName));
        var entity = save.getEntity();
        entity.equip((Armor)loadItem(save.getArmorID()));
        entity.equip((Weapon)loadItem(save.getWeaponID()));

        for(var itemID : save.getInventory()){
            var item = loadItem(itemID);
            if(item.getItemType() == ItemType.CONSUMABLE)
                entity.addToInventory((Consumable)item);
            else if(item.getItemType() == ItemType.EQUIPMENT)
                entity.addToInventory((Equipment)item);
        }

        var controller = EnemyBehaviourController.getTypeInstance(save.getControllerType(), entity);
        result = new EnemyType(save.getEnemyTypeID(), controller, save.getIconFilePath());
        EnemyTypeStorage.getInstance().add(result.getID(), result);

        return result;
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
