import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import exception.dice.InvalidDiceSideCountException;
import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import file.FileIOUtil;
import file.elements.EnemyMapData;
import file.elements.EnemyTypeSave;
import file.elements.GameConfigSave;
import file.elements.ItemSave;
import file.elements.PlayerProgressSave;
import file.handlers.FileHandler;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.Player;
import game.behaviour.entities.PlayerEntity;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.enemy.EnemyType;
import game.behaviour.entities.enemy.controller.BerserkerEnemyController;
import game.behaviour.entities.enemy.controller.RangerEnemyController;
import game.behaviour.weapons.Shotgun;
import game.enums.EnemyBehaviourControllerType;
import game.global.GameHandler;
import game.global.storage.EnemyTypeStorage;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.dataclass.MapLayoutData;
import game.utility.delegates.GenericDelegate;
import game.utility.dice.DiceRoller;
import ui.data.GridPosition;
import ui.elements.CharacterFrame;
import ui.elements.CombatFrame;
import ui.elements.GridEntityComponent;
import ui.elements.PlayFrame;
import ui.elements.PlayfieldPanel;
import ui.elements.UtilityButtonPanel;
import uilogic.GridButtonHandler;
import uilogic.UIHandler;

public class Main {

    public static void main(String[] args) throws Exception{
        //UIHandler.getInstance().displayPlayerDeath();
        DiceRoller.getInstance().setDefault(20);
        DiceRoller.getInstance().setDelegate(new GenericDelegate() {
            public void run(Object o){ UIHandler.getInstance().displayDiceRollResult((Integer)o); }
        });
        //GameHandler.getInstance().start();
        
        testEquipmentPanel();
        //createTestData();
    }

    static void testEquipmentPanel() throws ArgumentNullException, IOException, InvalidArgumentException, InvalidDiceSideCountException{
        var armor = (Armor)new Armor(15, 1).setName("Kevlar").setDescription("Simple but durable.").setID("kevlar-001");
        String path = "G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\img\\items\\kevlar.jpg";

        var weapon = (Shotgun)new Shotgun("shotgun-001", "Tech Shotgun", 0.5).setDamageDice(6).setDiceCount(3).setAttackModifier(4).setDamageModifier(2).setRange(3).setDescription("Good shit.");
        String path2 = "G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\img\\items\\tech-shotgun.png";

        CharacterFrame frame = new CharacterFrame();
        frame.getEquipmentPanel().getTopPanel().setUpContent(armor, path, 10);
        frame.getEquipmentPanel().getBotPanel().setUpContent(weapon, path2, 10);
        frame.setVisible(true);
    }

    static void createTestData() throws ArgumentNullException, InvalidArgumentException, FileNotFoundException, IOException, InvalidDiceSideCountException, ItemNotInInventoryException{
        var fileIO = new FileIOUtil();

        var data = new MapLayoutData("default-map-001", 20, 11, "maps/2.jpg", new GridPosition(11,8));
        data.setName("Defaul Map").setDescription("This is where player first spawns.");
        var enemyMapData = new EnemyMapData("shotgun-thug", "shotgun-thug-I-001").setPosition(new GridPosition(10, 5));
        var enemyMapData1 = new EnemyMapData("shotgun-thug", "shotgun-thug-I-002").setPosition(new GridPosition(12, 5));
        data.addEnemy(enemyMapData);
        data.addEnemy(enemyMapData1);

        String dataFileName = "default-map-001.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\mapdata\\" + dataFileName, data);

        var armor = (Armor)new Armor(15, 1).setName("Kevlar").setDescription("Simple but durable.").setID("kevlar-001");
        var weapon = (Shotgun)new Shotgun("shotgun-001", "Tech Shotgun", 0.5).setDamageDice(6).setDiceCount(3).setAttackModifier(4).setDamageModifier(2).setRange(3).setDescription("Good shit.");
        
        var armorSave = new ItemSave();
        armorSave.item = armor;
        armorSave.iconFilePath = "items/kevlar.jpg";

        var weaponSave = new ItemSave();
        weaponSave.item = weapon;
        weaponSave.iconFilePath = "items/tech-shotgun.png";

        String armorFileName = "kevlar-001.txt";
        String weaponFileName = "shotgun-001.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\item\\" + armorFileName, armorSave);        
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\item\\" + weaponFileName, weaponSave);

        var enemyEntity = (EnemyEntity)new EnemyEntity(20, 5, 1).setRewardXP(20).setName("Techno Punk");
        enemyEntity.equip(armor);
        enemyEntity.equip(weapon);

        var enemyData = new EnemyTypeSave();
        enemyData.enemyTypeID = "shotgun-thug";
        enemyData.controllerType = EnemyBehaviourControllerType.BERSERK;
        enemyData.entity = enemyEntity;
        enemyData.iconFilePath = "characters/7.png";
        enemyData.enemyArmorID = armor.getID();
        enemyData.enemyWeaponID = weapon.getID();

        String enemyFileName = "shotgun-thug.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\enemy\\" + enemyFileName, enemyData);

        var playerEntity = new PlayerEntity(30, 5, 1);
        var player = (Player)new Player("player", playerEntity).setName("Player");
        player.applyStats();
        var playerSave = new PlayerProgressSave();
        playerSave.player = player;
        playerSave.currentMapID = "default-map-001";
        playerSave.playerArmorID = armor.getID();
        playerSave.playerWeaponID = weapon.getID();
        playerSave.inventory.add(armor.getID());
        playerSave.inventory.add(weapon.getID());
        playerSave.currentIconFile = "characters/5.png";
        playerSave.playerPosition = data.getPlayerPosition();
        playerSave.modifiable = false;

        String playerFileName = "default-save.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\" + playerFileName, playerSave);

        var config = new GameConfigSave();
        config.defaultMapID = "default-map-001";
        config.enemyFolder = "enemy";
        config.itemFolder = "item";
        config.mapdataFolder = "mapdata";
        config.imageAssetFolder = "img";
        config.defaultPlayerSaveFile = "default-save.txt";

        String configFileName = "game-config.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\" + configFileName, config);
    }

    private static void test() throws Exception{
        DiceRoller.getInstance().setDefault(20);
        UIHandler.getInstance().start();

        var enemyArmor = (Armor)new Armor(15, 1).setName("Chaim Mail").setDescription("Simple but durable.").setID("chaim-mail-001");
        var enemyWeapon = (Shotgun)new Shotgun("shotgun-001", "Tech Shotgun", 0.5).setDamageDice(6).setDiceCount(3).setAttackModifier(4).setDamageModifier(2).setRange(3).setDescription("Good shit.");
        var enemyEntity = new EnemyEntity(20, 5, 1).setRewardXP(20);
        enemyEntity.equip(enemyArmor);
        enemyEntity.equip(enemyWeapon);

        var enemyController = new BerserkerEnemyController(enemyEntity);
        //var enemyType = new EnemyType("shotgun-thug", enemyController, "resources/img/characters/7.png");
        //var enemy = new Enemy("shotgun-thug-I-001", enemyType);
        //enemy.setPosition(new GridPosition(1,3));

        enemyController.addEventListeners(new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String arg = argument.getArgument().toString();
                try{ UIHandler.getInstance().getCombatLogger().addSystemLog(arg); }
                catch(ArgumentNullException e){}
            }
        }, new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String arg = argument.getArgument().toString();
                try{ UIHandler.getInstance().getCombatLogger().addSystemLog(arg); }
                catch(ArgumentNullException e){}
            }
        });

        //UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy, enemyType.getIconFilePath());

        var playerEntity = new PlayerEntity(100, 5, 2);
        var player = new Player("player", playerEntity);
        player.addToInventory(enemyArmor);
        player.equip(enemyArmor);

        enemyController.runEnemy(player, 2);

        System.out.println(player.getCurrentHealth());
    }

    private static void playFieldTest() throws Exception{
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        var handler = new GridButtonHandler(new GenericDelegate() { public void run(Object o){} });
        var layout = new MapLayoutData("map-01", 20, 11, "resources/img/maps/2.jpg", new GridPosition(0,0));
        var field = new PlayfieldPanel(1200, 675).setMapLayout(layout, handler, false);
        frame.add(field);

        var size = field.getComponentSize();
        var gridPos = new GridPosition(6, 1);
        var entity = new GridEntityComponent("punk-01", size.getHorizontal(), size.getVertical(), gridPos).setImage("resources/img/characters/7.png");
        var gridPos2 = new GridPosition(3, 4);
        var entity2 = new GridEntityComponent("punk-02", size.getHorizontal(), size.getVertical(), gridPos2).setImage("resources/img/characters/6.png");
        field.addEntity(entity);
        field.addEntity(entity2);

        frame.revalidate();
        frame.pack();
        frame.setVisible(true);

        Thread.sleep(3000);

        field.replaceEntity(entity, new GridPosition(4, 3));
        frame.revalidate();
    }
}