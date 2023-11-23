import java.io.File;

import file.FileIOUtil;
import file.elements.EnemyMapData;
import file.elements.EnemyTypeSave;
import file.elements.GameConfigSave;
import file.elements.ItemSave;
import file.elements.PlayerProgressSave;
import game.behaviour.Consumable;
import game.behaviour.Item;
import game.behaviour.abstracts.Armor;
import game.behaviour.entities.Player;
import game.behaviour.entities.PlayerEntity;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.weapons.AutoPistol;
import game.behaviour.weapons.Shotgun;
import game.enums.EnemyBehaviourControllerType;
import game.enums.ModifierType;
import game.utility.dataclass.MapLayoutData;
import ui.data.GridPosition;

public class DataCreator {

    private static FileIOUtil fileIO;
    public static String datafolder = "G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\";

    public static void createTestData() throws Exception{
        fileIO = new FileIOUtil();

        var defaultMap = new MapLayoutData("default-map-001", 20, 11, "maps/2.jpg", new GridPosition(11,8));
        defaultMap.setName("Defaul Map").setDescription("This is where player first spawns.");
        var enemyMapData = new EnemyMapData("shotgun-thug", "shotgun-thug-I-001").setPosition(new GridPosition(10, 5));
        var enemyMapData1 = new EnemyMapData("shotgun-thug", "shotgun-thug-I-002").setPosition(new GridPosition(12, 5));
        var enemyMapData2 = new EnemyMapData("shotgun-thug", "shotgun-thug-I-003").setPosition(new GridPosition(14, 5));
        defaultMap.addEnemy(enemyMapData);
        defaultMap.addEnemy(enemyMapData1);
        defaultMap.addEnemy(enemyMapData2);
        String dataFileName = "default-map-001.txt";
        fileIO.writeObjectToFile(new File(datafolder, "mapdata/" + dataFileName).getAbsolutePath(), defaultMap);

        var kevlar = (Armor)new Armor(15, 0).setName("Kevlar").setID("kevlar-001");
        kevlar.setDescription("Simple but durable body armor.\n\nIt was the standard issue NCPD kevlar before the Big Data Crash.");
        var fastBoots = (Armor)new Armor(10, 3).setName("Comfortable Jumpers").setID("fast-boots-001");
        fastBoots.setDescription("Very comfortable leggings that allow the wearer to move around faster.\n\n 'Look at you jumpin' round!'");
        var shotgun = (Shotgun)new Shotgun("shotgun-001", "Tech Shotgun", 0.5).setDamageDice(6).setDiceCount(3).setAttackModifier(4).setDamageModifier(2).setRange(3);
        shotgun.setDescription("Modified shotgun that shoots electrified bullets. Attacks with this weapon are guaranteed to penetrate enemy armor, if the enemy is closer than " + (shotgun.getHitRange() * shotgun.getRange()) + ".\n\n This shotgun was handcrafted by the nomads of the Badlands, but streetkids usually prefer them as the electric bullets work best against Arasaka drones.");
        var autoPistol = (AutoPistol)new AutoPistol("autopistol-001", "Auto Pistol").setDamageDice(10).setDiceCount(3).setAttackModifier(5).setDamageModifier(4).setRange(6);
        autoPistol.setDescription("Simple autopistol. Can be used " + autoPistol.getAttacksInRound() + " times in a round. \n\nThe most basic pistol for any streetkid. Used to be the standard issue sidearm for NCPD officers, but it is considered out-of-date since the Data Crash.");
        var randomMessage = (Consumable)new Consumable("message-001", ModifierType.MOVEMENT, 1).setName("Message");
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

        var enemyData = new EnemyTypeSave();
        enemyData.enemyTypeID = "shotgun-thug";
        enemyData.controllerType = EnemyBehaviourControllerType.BERSERK;
        enemyData.entity = enemyEntity;
        enemyData.iconFilePath = "characters/7.png";
        enemyData.enemyArmorID = kevlar.getID();
        enemyData.enemyWeaponID = shotgun.getID();

        String enemyFileName = "shotgun-thug.txt";
        fileIO.writeObjectToFile("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata\\enemy\\" + enemyFileName, enemyData);

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
        playerSave.inventory.add(randomMessage.getID());
        playerSave.currentIconFile = "characters/5.png";
        playerSave.playerPosition = defaultMap.getPlayerPosition();
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
}