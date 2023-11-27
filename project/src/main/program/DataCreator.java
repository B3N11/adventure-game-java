package main.program;
import java.io.File;

import main.file.FileIOUtil;
import main.file.elements.EnemyMapData;
import main.file.elements.EnemyTypeSave;
import main.file.elements.GameConfigSave;
import main.file.elements.ItemMapData;
import main.file.elements.ItemSave;
import main.file.elements.MapLayoutData;
import main.file.elements.PlayerProgressSave;
import main.game.behaviour.entities.enemy.EnemyEntity;
import main.game.behaviour.entities.player.Player;
import main.game.behaviour.entities.player.PlayerEntity;
import main.game.behaviour.items.Item;
import main.game.behaviour.items.equipment.Armor;
import main.game.behaviour.items.equipment.weapons.AutoPistol;
import main.game.behaviour.items.equipment.weapons.Shotgun;
import main.game.enums.EnemyBehaviourControllerType;
import main.uilogic.GridPosition;

/*
 * This class is used to create the test data for the game.
 */
public class DataCreator {

    private static FileIOUtil fileIO;
    //public static String datafolder = "G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\";
    public static String datafolder = "G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata";

    /*
     * This method creates the test data for the game.
     */
    public static void createTestData() throws Exception{
        fileIO = new FileIOUtil();

        var defaultMap = new MapLayoutData("default-map-001", 9, 5, "maps/nightTimeSquare.png", new GridPosition(8, 2));
        defaultMap.setName("The Street of Awakening").setDescription("This street is only visited by the drunk and the homeless. Yet you find yourself here, when you are neither.");
        var enemyMapData = new EnemyMapData("shotgun-thug", "shotgun-thug-I-001").setPosition(new GridPosition(1, 2));
        var enemyMapData1 = new EnemyMapData("shotgun-thug", "shotgun-thug-I-002").setPosition(new GridPosition(4, 1));
        var itemMapData = new ItemMapData();
        itemMapData.itemID = "message-001";
        itemMapData.position = new GridPosition(2,3);
        defaultMap.addEnemy(enemyMapData);
        defaultMap.addEnemy(enemyMapData1);
        defaultMap.addItem(itemMapData);
        var alleyMap = new MapLayoutData("market-alley-001", 11, 6, "maps/nightTimeAlley.png", new GridPosition(6, 4));
        alleyMap.setName("Market Alley").setDescription("Smell of rain, fresh meat and gunpowder. Market Alley is the place where you can find trouble just as easily as a decent meal.");
        var enemyMapData2 = new EnemyMapData("market-enemy", "market-enemy-I-001").setPosition(new GridPosition(2, 3));
        alleyMap.addEnemy(enemyMapData2);

        String dataFileName = "default-map-001.txt";
        String alleyFileName = "market-alley-001.txt";
        fileIO.writeObjectToFile(new File(datafolder, "mapdata/" + dataFileName).getAbsolutePath(), defaultMap);
        fileIO.writeObjectToFile(new File(datafolder, "mapdata/" + alleyFileName).getAbsolutePath(), alleyMap);

        var kevlar = (Armor)new Armor(15, 0).setName("Kevlar").setID("kevlar-001");
        kevlar.setDescription("Simple but durable body armor.\n\nIt was the standard issue NCPD kevlar before the Big Data Crash.");
        var fastBoots = (Armor)new Armor(10, 3).setName("Comfortable Jumpers").setID("fast-boots-001");
        fastBoots.setDescription("Very comfortable leggings that allow the wearer to move around faster.\n\n 'Look at you jumpin' round!'");
        var shotgun = (Shotgun)new Shotgun("shotgun-001", "Tech Shotgun", 0.5).setDamageDice(6).setDiceCount(3).setAttackModifier(4).setDamageModifier(2).setRange(3);
        shotgun.setDescription("Modified shotgun that shoots electrified bullets. Attacks with this weapon are guaranteed to penetrate enemy armor, if the enemy is closer than " + (shotgun.getHitRange() * shotgun.getRange()) + " units.\n\n This shotgun was handcrafted by the nomads of the Badlands, but streetkids usually prefer them as the electric bullets work best against Arasaka drones.");
        var autoPistol = (AutoPistol)new AutoPistol("autopistol-001", "Auto Pistol").setDamageDice(10).setDiceCount(3).setAttackModifier(5).setDamageModifier(4).setRange(6);
        autoPistol.setDescription("Simple autopistol. Can be used " + autoPistol.getAttacksInRound() + " times in a round. \n\nThe most basic pistol for any streetkid. Used to be the standard issue sidearm for NCPD officers, but it is considered out-of-date since the Data Crash.");
        var randomMessage = (Item)new Item().setName("Message").setID("message-001");
        randomMessage.setDescription("\"All those moments will be lost in time, like tears in rain.\"");

        var kevlarSave = new ItemSave();
        kevlarSave.item = kevlar;
        kevlarSave.iconFilePath = "items/kevlar-common.jpg";
        var fastBootsSave = new ItemSave();
        fastBootsSave.item = fastBoots;
        fastBootsSave.iconFilePath = "items/fast-boots-rare.png";
        var shotgunSave = new ItemSave();
        shotgunSave.item = shotgun;
        shotgunSave.iconFilePath = "items/tech-shotgun.png";
        var autoPistolSave = new ItemSave();
        autoPistolSave.item = autoPistol;
        autoPistolSave.iconFilePath = "items/auto-pistol-epic.png";
        var messageSave = new ItemSave();
        messageSave.item = randomMessage;
        messageSave.iconFilePath = "items/data-key.png";

        String kevlarFileName = "kevlar-001.txt";
        String fastBootsFileName = "fast-boots-001.txt";
        String shotgunFileName = "shotgun-001.txt";
        String autoPistolFileName = "autopistol-001.txt";
        String messageFileName = "message-001.txt";
        fileIO.writeObjectToFile(new File(datafolder, "item/" + kevlarFileName).getAbsolutePath(), kevlarSave);
        fileIO.writeObjectToFile(new File(datafolder, "item/" + fastBootsFileName).getAbsolutePath(), fastBootsSave);
        fileIO.writeObjectToFile(new File(datafolder, "item/" + shotgunFileName).getAbsolutePath(), shotgunSave);
        fileIO.writeObjectToFile(new File(datafolder, "item/" + autoPistolFileName).getAbsolutePath(), autoPistolSave);
        fileIO.writeObjectToFile(new File(datafolder, "item/" + messageFileName).getAbsolutePath(), messageSave);

        var enemyEntity = (EnemyEntity)new EnemyEntity(20, 5, 1).setRewardXP(20).setName("Techno Punk");
        enemyEntity.equip(kevlar);
        enemyEntity.equip(shotgun);

        var enemyEntity2 = (EnemyEntity)new EnemyEntity(20, 5, 1).setRewardXP(20).setName("Market Thug");
        enemyEntity2.equip(fastBoots);
        enemyEntity2.equip(autoPistol);

        var enemyData = new EnemyTypeSave();
        enemyData.enemyTypeID = "shotgun-thug";
        enemyData.controllerType = EnemyBehaviourControllerType.BERSERK;
        enemyData.entity = enemyEntity;
        enemyData.iconFilePath = "characters/7.png";
        enemyData.enemyArmorID = kevlar.getID();
        enemyData.enemyWeaponID = shotgun.getID();

        var enemyData2 = new EnemyTypeSave();
        enemyData2.enemyTypeID = "market-enemy";
        enemyData2.controllerType = EnemyBehaviourControllerType.BERSERK;
        enemyData2.entity = enemyEntity2;
        enemyData2.iconFilePath = "characters/9.png";
        enemyData2.enemyArmorID = fastBoots.getID();
        enemyData2.enemyWeaponID = autoPistol.getID();

        String enemyFileName = "shotgun-thug.txt";
        String enemy2FileName = "market-enemy.txt";
        fileIO.writeObjectToFile(new File(datafolder, "enemy\\" + enemyFileName).getAbsolutePath(), enemyData);
        fileIO.writeObjectToFile(new File(datafolder, "enemy\\" + enemy2FileName).getAbsolutePath(), enemyData2);

        var playerEntity = new PlayerEntity(30, 5, 1);
        var player = (Player)new Player("player", playerEntity).setName("Player");
        player.applyStats();
        var playerSave = new PlayerProgressSave();
        playerSave.player = player;
        playerSave.currentMapID = "default-map-001";
        playerSave.playerArmorID = kevlar.getID();
        playerSave.playerWeaponID = shotgun.getID();
        playerSave.inventory.add(kevlar.getID());
        playerSave.inventory.add(fastBoots.getID());
        playerSave.inventory.add(shotgun.getID());
        playerSave.inventory.add(autoPistol.getID());
        playerSave.currentIconFile = "characters/5.png";
        playerSave.playerPosition = defaultMap.getPlayerPosition();
        playerSave.modifiable = false;

        String playerFileName = "default-save.txt";
        fileIO.writeObjectToFile(new File(datafolder, playerFileName).getAbsolutePath(), playerSave);

        var config = new GameConfigSave();
        config.defaultMapID = "default-map-001";
        config.enemyFolder = "enemy";
        config.itemFolder = "item";
        config.mapdataFolder = "mapdata";
        config.imageAssetFolder = "img";
        config.defaultPlayerSaveFile = "default-save.txt";

        String configFileName = "game-config.txt";
        fileIO.writeObjectToFile(new File(datafolder, configFileName).getAbsolutePath(), config);
    }
}