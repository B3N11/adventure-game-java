package file.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.ElementAlreadyInCollectionException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import file.FileIOUtil;
import file.elements.EnemyTypeSave;
import file.elements.GameConfigSave;
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
import game.utility.dataclass.MapLayoutData;
import uilogic.UIHandler;

public class FileHandler {
    
    private boolean started;

    private FileIOUtil fileIOUtil;

    private File itemFolderFilePath;
    private File enemyTypeFolderFilePath;
    private File mapLayoutFolderFilePath;

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

    public void loadConfigFile(String filePath) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException{
        if(filePath == null)
            throw new ArgumentNullException();

        var config = (GameConfigSave)fileIOUtil.readObjectFromFile(filePath);
        String folderPath = new File(filePath).getParent();

        itemFolderFilePath = new File(folderPath, config.itemFolder);
        enemyTypeFolderFilePath = new File(folderPath, config.enemyFolder);
        mapLayoutFolderFilePath = new File(folderPath, config.mapdataFolder);

        if(!itemFolderFilePath.exists() || !enemyTypeFolderFilePath.exists() || !mapLayoutFolderFilePath.exists())
            throw new FileNotFoundException();

        UIHandler.getInstance().showMessage("Config successfully loaded!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void loadPlayerProgressSave(String filePath) throws ArgumentNullException, FileNotFoundException, ClassNotFoundException, IOException, ElementAlreadyInCollectionException, ItemNotInInventoryException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(filePath == null)
            throw new ArgumentNullException();

        var playerProgress = (PlayerProgressSave)fileIOUtil.readObjectFromFile(filePath);
        var player = playerProgress.player;

        for(var item : playerProgress.inventory){
            var itemObject = loadItem(item);

            if(itemObject.getItemType() == ItemType.EQUIPMENT)
                player.addToInventory((Equipment)itemObject);
            if(itemObject.getItemType() == ItemType.CONSUMABLE)
                player.addToInventory((Consumable)itemObject);
        }

        //TODO: Implement file format exception

        //TODO: Implement modified enemy load

        var armor = (Armor)loadItem(playerProgress.playerArmorID);
        var weapon = (Weapon)loadItem(playerProgress.playerWeaponID);
        player.equip(armor);
        player.equip(weapon);
        GameHandler.getInstance().setSessionPlayer(player);
        loadCurrentMap(playerProgress.currentMapID);

        //TODO: Implement enemy placement
    }

    private void loadModifiedEnemies(){

    }

    public void loadCurrentMap(String id) throws FileNotFoundException, ClassNotFoundException, ArgumentNullException, IOException, ItemNotInInventoryException, ElementAlreadyInCollectionException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        String fileName = id + ".txt";
        var mapData = (MapLayoutData)fileIOUtil.readObjectFromFile(new File(mapLayoutFolderFilePath, fileName));

        for(var enemyData : mapData.getEnemies()){

            var enemyType = loadEnemyType(enemyData.getAssetID());
            var enemy = new Enemy(enemyData.getInstanceID(), enemyType);

            if(!ModifiedEnemyStorage.getInstance().contains(enemyData.getInstanceID())){
                ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);
                UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy, enemyType.getIconFilePath());
                continue;
            }
            
            var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getID());
            enemy.setPosition(modifiedData.getPosition());
            enemy.setCurrentHealth(modifiedData.getHealth());

            if(enemy.getCurrentHealth() == 0)
                continue;
            
            ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);
            UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy, enemyType.getIconFilePath());

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
        var entity = save.entity;
        entity.equip((Armor)loadItem(save.enemyArmorID));
        entity.equip((Weapon)loadItem(save.enemyWeaponID));

        for(var itemID : save.inventory){
            var item = loadItem(itemID);
            if(item.getItemType() == ItemType.CONSUMABLE)
                entity.addToInventory((Consumable)item);
            else if(item.getItemType() == ItemType.EQUIPMENT)
                entity.addToInventory((Equipment)item);
        }

        var controller = EnemyBehaviourController.getTypeInstance(save.controllerType, entity);
        result = new EnemyType(save.enemyTypeID, controller, save.iconFilePath);
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
