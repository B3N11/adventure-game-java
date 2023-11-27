package main.game.global;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.exception.entity.ItemNotInInventoryException;
import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.file.FileIOUtil;
import main.file.elements.EnemyTypeSave;
import main.file.elements.GameConfigSave;
import main.file.elements.IconData;
import main.file.elements.ItemSave;
import main.file.elements.MapLayoutData;
import main.file.elements.PlayerProgressSave;
import main.game.behaviour.abstracts.EnemyBehaviourController;
import main.game.behaviour.abstracts.Equipment;
import main.game.behaviour.abstracts.Weapon;
import main.game.behaviour.entities.enemy.Enemy;
import main.game.behaviour.entities.enemy.EnemyType;
import main.game.behaviour.items.Consumable;
import main.game.behaviour.items.Item;
import main.game.behaviour.items.equipment.Armor;
import main.game.enums.ItemType;
import main.game.global.storage.ActiveEnemyStorage;
import main.game.global.storage.EnemyTypeStorage;
import main.game.global.storage.IconDataStorage;
import main.game.global.storage.ItemStorage;
import main.game.global.storage.MapStorage;
import main.game.global.storage.ModifiedEnemyStorage;
import main.game.utility.ModifiedEnemyData;
import main.game.utility.event.Event;
import main.game.utility.event.EventArgument;
import main.game.utility.event.IEventListener;

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

    /**
     * Singleton pattern method to get the instance of FileHandler.
     * If the instance is null, a new instance is created.
     * @return the instance of FileHandler.
     */
    public static FileHandler getInstance() {
        if(instance == null)
            instance = new FileHandler();
        return instance;
    }

    /**
     * Loads the configuration file from the given file path.
     * It also loads the maps and player progress save.
     * @param filePath The path of the configuration file.
     * @throws Exception if the filePath is null or if the file does not exist.
     */
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

        loadMaps();
        loadPlayerProgressSave(new File(folderPath, config.defaultPlayerSaveFile).getAbsolutePath());
        UIHandler.getInstance().showMessage("Config successfully loaded!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Loads the maps from the map layout folder.
     * If the file is a directory, it is skipped.
     * If an exception occurs while reading the file, it is ignored.
     */
    private void loadMaps(){
        for(var file : mapLayoutFolderFilePath.listFiles()){
            if(file.isDirectory())
                continue;
            
            try{
                var map = (MapLayoutData)fileIOUtil.readObjectFromFile(file.getAbsolutePath());
                MapStorage.getInstance().add(map.getName(), map.getID());   //Switched key-value pairs, we need to find ID based on name
            }catch(Exception e){}
        }
    }

    /**
     * Loads the player progress save from the given file path.
     * @param filePath The path of the player progress save file.
     * @throws Exception if an error occurs while loading the player progress save.
     */
    public void loadPlayerProgressSave(String filePath) throws Exception{
        if(filePath == null)
            throw new ArgumentNullException();

        var playerProgress = (PlayerProgressSave)fileIOUtil.readObjectFromFile(filePath);
        var player = playerProgress.player;

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
            player.addToInventory(itemObject);
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

        GameHandler.getInstance().setSessionPlayer(player);
        
        //Loads map and places player on grid
        loadCurrentMap(playerProgress.currentMapID, player.getEntity().getInventory().getAllItems());

        //replaces player to saved position
        UIHandler.getInstance().getPlayFieldHandler().replaceEntity(player.getInstanceID(), playerProgress.playerPosition);

        GameHandler.getInstance().getSaveHandler().setCurrentSavePath(filePath);
        GameHandler.getInstance().getSaveHandler().setModifiable(playerProgress.modifiable);
    }

    /**
     * Loads the modified enemies from the given data and adds them to the ModifiedEnemyStorage.
     * @param data The list of modified enemy data.
     * @throws ArgumentNullException if the data is null.
     */
    private void loadModifiedEnemies(List<ModifiedEnemyData> data) throws ArgumentNullException{
        if(data == null)
            throw new ArgumentNullException();
            
        for(var enemy : data){
            try{ ModifiedEnemyStorage.getInstance().add(enemy.getID(), enemy); }
            catch(ArgumentNullException e){}
        }
    }

    /**
     * Loads the current map from the given id and player inventory.
     * Modifies the file path to full path.
     * Clears the ActiveEnemyStorage.
     * Sets the current map layout in the GameHandler.
     * Loads the enemies on the map.
     * @param id The id of the map.
     * @param playerInventory The player's inventory.
     * @throws Exception if an error occurs while loading the map.
     */
    public void loadCurrentMap(String id, List<Item> playerInventory) throws Exception{
        String fileName = id + ".txt";
        var mapData = (MapLayoutData)fileIOUtil.readObjectFromFile(new File(mapLayoutFolderFilePath, fileName));

        //Modify file path to full path
        mapData.setBackgroundFilePath(new File(imageAssetFolderFilePath, mapData.getBackgroundFilePath()).getAbsolutePath());

        ActiveEnemyStorage.getInstance().clear();

        GameHandler.getInstance().setCurrentMapLayout(mapData);

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

        for(var itemData : mapData.getItems()){
            var item = loadItem(itemData.itemID);
            String itemIconPath = IconDataStorage.getInstance().get(item.getID()).getAbsolutPath();

            if(playerInventory.contains(item))
                continue;

            UIHandler.getInstance().getPlayFieldHandler().placeEntity(item.getID(), itemData.position, itemIconPath);
        }

        String playerIconPath = IconDataStorage.getInstance().get(GameHandler.getInstance().getPlayer().getInstanceID()).getAbsolutPath();
        UIHandler.getInstance().getPlayFieldHandler().placeEntity(GameHandler.getInstance().getPlayer().getInstanceID(), mapData.getPlayerPosition(), playerIconPath);
    }

        /**
     * Loads the enemy from the given id.
     * If the enemy is already loaded, it returns the existing enemy.
     * Otherwise, it loads the enemy from the save file.
     * It also loads the enemy's icon data and adds it to the IconDataStorage.
     * Adds the enemy to the EnemyTypeStorage.
     * @param id The id of the enemy.
     * @return The loaded enemy.
     * @throws ArgumentNullException if the id is null.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     * @throws IOException if an I/O error occurs.
     */
    private EnemyType loadEnemyType(String id) throws ArgumentNullException, ClassNotFoundException, IOException, ItemNotInInventoryException{
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
                String message = "Damaged the target: " + arg[1] + "\nTarget current health: " + arg[2];
                try{ UIHandler.getInstance().getCombatLogger().addEntityLog(arg[0], message); }
                catch(ArgumentNullException e){}
            }
        });

        var iconData = new IconData(save.iconFilePath, new File(imageAssetFolderFilePath,save.iconFilePath).getAbsolutePath());
        IconDataStorage.getInstance().add(id, iconData);
        result = new EnemyType(save.enemyTypeID, controller);
        EnemyTypeStorage.getInstance().add(result.getID(), result);

        return result;
    }

     /**
     * Loads the item from the given id.
     * If the item is already loaded, it returns the existing item.
     * Otherwise, it loads the item from the save file.
     * It also loads the item's icon data and adds it to the IconDataStorage.
     * @param id The id of the item.
     * @return The loaded item.
     * @throws ArgumentNullException if the id is null.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     * @throws IOException if an I/O error occurs.
     */
    private Item loadItem(String id) throws ArgumentNullException, ClassNotFoundException, IOException{
        if(id == null)
            throw new ArgumentNullException();
        
        var result = (Item)ItemStorage.getInstance().get(id);
        if(result != null)
            return result;

        String fileName = id + ".txt";
        var itemSave = (ItemSave)fileIOUtil.readObjectFromFile(new File(itemFolderFilePath, fileName));
        result = itemSave.item;

        var iconData = new IconData(itemSave.iconFilePath, new File(imageAssetFolderFilePath, itemSave.iconFilePath).getAbsolutePath());
        IconDataStorage.getInstance().add(result.getID(), iconData);

        //Add to storage for later use
        ItemStorage.getInstance().add(result.getID(), result);

        return result;
    }

    /**
     * Saves the current game progress to the given file path.
     * It saves the current map, player progress, and modified enemies.
     * @param filePath The path where the progress will be saved.
     * @throws ArgumentNullException if the filePath is null.
     */
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

        var allItems = player.getEntity().getInventory().getAllItems();
        
        for(var enemy : ModifiedEnemyStorage.getInstance().entrySet())
            progress.modifiedEnemies.add(enemy.getValue());

        for(var item : allItems)
            progress.inventory.add(item.getID());

        String file = filePath;
        if(appendFileExtension)
            file += ".txt";

        try{ fileIOUtil.writeObjectToFile(file, progress); }
        catch(Exception e){}
    }
}