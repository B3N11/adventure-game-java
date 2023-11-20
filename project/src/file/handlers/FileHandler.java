package file.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.ElementAlreadyInCollectionException;
import exception.general.ElementNotFoundException;
import file.FileIOUtil;
import file.elements.EnemyTypeSave;
import file.elements.GameConfigSave;
import file.elements.IconData;
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
import game.global.storage.IconDataStorage;
import game.global.storage.ItemStorage;
import game.global.storage.ModifiedEnemyStorage;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.dataclass.MapLayoutData;
import game.utility.dataclass.ModifiedEnemyData;
import uilogic.UIHandler;

public class FileHandler {

    private FileIOUtil fileIOUtil;

    private File itemFolderFilePath;
    private File enemyTypeFolderFilePath;
    private File mapLayoutFolderFilePath;
    private File imageAssetFolderFilePath;

    private static FileHandler instance;

    private FileHandler(){
        fileIOUtil = new FileIOUtil();
    }

    public static FileHandler getInstance() {
        if(instance == null)
            instance = new FileHandler();
        return instance;
    }

    public void loadConfigFile(String filePath) throws Exception{
        if(filePath == null)
            throw new ArgumentNullException();

        var config = (GameConfigSave)fileIOUtil.readObjectFromFile(filePath);
        String folderPath = new File(filePath).getParent();

        itemFolderFilePath = new File(folderPath, config.itemFolder);
        enemyTypeFolderFilePath = new File(folderPath, config.enemyFolder);
        mapLayoutFolderFilePath = new File(folderPath, config.mapdataFolder);
        imageAssetFolderFilePath = new File(folderPath, config.imageAssetFolder);

        if(!itemFolderFilePath.exists() || !enemyTypeFolderFilePath.exists() || !mapLayoutFolderFilePath.exists() || !imageAssetFolderFilePath.exists())
            throw new FileNotFoundException();

        loadPlayerProgressSave(new File(folderPath, config.defaultPlayerSaveFile).getAbsolutePath());
        UIHandler.getInstance().showMessage("Config successfully loaded!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void loadPlayerProgressSave(String filePath) throws Exception{
        if(filePath == null)
            throw new ArgumentNullException();

        var playerProgress = (PlayerProgressSave)fileIOUtil.readObjectFromFile(filePath);
        var player = playerProgress.player;
        GameHandler.getInstance().setSessionPlayer(player);

        IconDataStorage.getInstance().clear();
        ModifiedEnemyStorage.getInstance().clear();
        ActiveEnemyStorage.getInstance().clear();
        ItemStorage.getInstance().clear();
        EnemyTypeStorage.getInstance().clear();

        var iconData = new IconData(playerProgress.currentIconFile, new File(imageAssetFolderFilePath, playerProgress.currentIconFile).getAbsolutePath());
        iconData.setID(player.getID());
        IconDataStorage.getInstance().add(player.getInstanceID(), iconData);

        for(var item : playerProgress.inventory){
            var itemObject = loadItem(item);

            if(itemObject.getItemType() == ItemType.EQUIPMENT)
                player.addToInventory((Equipment)itemObject);
            if(itemObject.getItemType() == ItemType.CONSUMABLE)
                player.addToInventory((Consumable)itemObject);
        }

        var armor = (Armor)loadItem(playerProgress.playerArmorID);
        var weapon = (Weapon)loadItem(playerProgress.playerWeaponID);
        player.equip(armor);
        player.equip(weapon);
        

        player.addEventListeners(new IEventListener() {
            public void run(EventArgument arg, Event e) { GameHandler.getInstance().handlePlayerDeath(); }
        }, new IEventListener() {
            public void run(EventArgument arg, Event e) { GameHandler.getInstance().handlePlayerLevelUp(); }
        });
        
        try{ loadModifiedEnemies(playerProgress.modifiedEnemies); }
        catch(ArgumentNullException e){}

        //Loads map and places player on grid
        loadCurrentMap(playerProgress.currentMapID);
        
        //replaces player to saved position
        UIHandler.getInstance().getPlayFieldHandler().replaceEntity(player.getInstanceID(), playerProgress.playerPosition);

        GameHandler.getInstance().getSaveHandler().setCurrentSavePath(filePath);
        GameHandler.getInstance().getSaveHandler().setModifiable(playerProgress.modifiable);
    }

    private void loadModifiedEnemies(List<ModifiedEnemyData> data) throws ArgumentNullException{
        if(data == null)
            throw new ArgumentNullException();
            
        for(var enemy : data){
            try{ ModifiedEnemyStorage.getInstance().add(enemy.getID(), enemy); }
            catch(ArgumentNullException e){}
        }
    }

    public void loadCurrentMap(String id) throws Exception{
        String fileName = id + ".txt";
        var mapData = (MapLayoutData)fileIOUtil.readObjectFromFile(new File(mapLayoutFolderFilePath, fileName));

        //Modify file path to full path
        mapData.setBackgroundFilePath(new File(imageAssetFolderFilePath, mapData.getBackgroundFilePath()).getAbsolutePath());

        ActiveEnemyStorage.getInstance().clear();

        UIHandler.getInstance().getPlayFieldHandler().setCurrentMapLayout(mapData);

        for(var enemyData : mapData.getEnemies()){

            var enemyType = loadEnemyType(enemyData.getAssetID());
            var enemy = new Enemy(enemyData.getInstanceID(), enemyType);
            enemy.applyStats();

            String enemyIconPath = IconDataStorage.getInstance().get(enemy.getEnemyType().getID()).getAbsolutPath();

            if(!ModifiedEnemyStorage.getInstance().contains(enemyData.getInstanceID())){
                ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);
                UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy.getInstanceID(), enemyData.getPosition(), enemyIconPath);
                continue;
            }
            
            var modifiedData = ModifiedEnemyStorage.getInstance().get(enemy.getID());
            var modifiedPosition = modifiedData.getPosition();
            enemy.setCurrentHealth(modifiedData.getHealth());

            if(enemy.isDead())
                continue;
            
            ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);
            UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy.getInstanceID(), modifiedPosition, enemyIconPath);
        }

        String playerIconPath = IconDataStorage.getInstance().get(GameHandler.getInstance().getPlayer().getInstanceID()).getAbsolutPath();
        UIHandler.getInstance().getPlayFieldHandler().placeEntity(GameHandler.getInstance().getPlayer().getInstanceID(), mapData.getPlayerPosition(), playerIconPath);
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

        if(save.inventory != null){
            for(var itemID : save.inventory){
                var item = loadItem(itemID);
                if(item.getItemType() == ItemType.CONSUMABLE)
                    entity.addToInventory((Consumable)item);
                else if(item.getItemType() == ItemType.EQUIPMENT)
                    entity.addToInventory((Equipment)item);
            }
        }

        var controller = EnemyBehaviourController.getTypeInstance(save.controllerType, entity);

        //Add event listeners (logger)
        controller.addEventListeners(new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String[] arg = argument.getArgument().toString().split(":");
                String message = "Attacked the target: " + arg[1];
                try{ UIHandler.getInstance().getCombatLogger().addEntityLog(arg[0], message); }
                catch(ArgumentNullException e){}
            }
        }, new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String[] arg = argument.getArgument().toString().split(":");
                String message = "Damaged the target: " + arg[1];
                try{ UIHandler.getInstance().getCombatLogger().addEntityLog(arg[0], message);; }
                catch(ArgumentNullException e){}
            }
        });

        var iconData = new IconData(save.iconFilePath, new File(imageAssetFolderFilePath,save.iconFilePath).getAbsolutePath());
        IconDataStorage.getInstance().add(id, iconData);
        result = new EnemyType(save.enemyTypeID, controller);
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

    public void saveProgress(String filePath, boolean appendFileExtension) throws ArgumentNullException{
        if(filePath == null)
            throw new ArgumentNullException();

        var player = GameHandler.getInstance().getPlayer();

        var progress = new PlayerProgressSave();
        progress.currentIconFile = IconDataStorage.getInstance().get(player.getID()).getNormalPath();
        progress.player = player;
        progress.currentMapID = UIHandler.getInstance().getPlayFieldHandler().getCurrentMapLayoutData().getID();
        progress.modifiable = true;
        try{ progress.playerPosition = UIHandler.getInstance().getPlayFieldHandler().getEntityPositionByID(player.getID()); }
        catch(ElementNotFoundException e){}
        progress.playerArmorID = player.getEntity().getArmor().getID();
        progress.playerWeaponID = player.getEntity().getWeapon().getID();

        progress.inventory = new ArrayList<>();
        progress.modifiedEnemies = new ArrayList<>();
        
        for(var enemy : ModifiedEnemyStorage.getInstance().entrySet())
            progress.modifiedEnemies.add(enemy.getValue());

        for(var item : player.getEntity().getInventory().getConsumables())
            progress.inventory.add(item.getID());

        for(var item : player.getEntity().getInventory().getEquipments())
            progress.inventory.add(item.getID());

        String file = filePath;
        if(appendFileExtension)
            file += ".txt";

        try{ fileIOUtil.writeObjectToFile(file, progress); }
        catch(Exception e){}
    }
}
