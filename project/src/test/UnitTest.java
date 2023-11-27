package test;

import main.game.global.DiceRoller;
import main.game.global.storage.ActiveEnemyStorage;
import main.uilogic.GridPosition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.exception.dice.DefaultDiceNotSetException;
import main.exception.entity.ItemNotInInventoryException;
import main.game.behaviour.Inventory;
import main.game.behaviour.entities.enemy.Enemy;
import main.game.behaviour.entities.enemy.EnemyEntity;
import main.game.behaviour.entities.enemy.EnemyType;
import main.game.behaviour.entities.enemy.controller.BerserkerEnemyController;
import main.game.behaviour.entities.player.Player;
import main.game.behaviour.entities.player.PlayerEntity;
import main.game.behaviour.items.Consumable;
import main.game.behaviour.items.Item;
import main.game.behaviour.items.equipment.*;
import main.game.behaviour.items.equipment.weapons.Shotgun;
import main.game.enums.ModifierType;
import main.game.utility.event.Event;
import main.game.utility.event.EventArgument;
import main.game.utility.event.IEventListener;
import main.uilogic.GridPosition;

public class UnitTest {

    private Enemy enemy;
    private Player player;

    public int playerLeveledUp;
    public boolean playerDied;

    @Before
    public void setUp() throws Exception{
        var weapon = new Shotgun("shotgun-0", "Shotgun", 0.5).setAttackModifier(5).setDamageDice(4).setDamageModifier(2).setDiceCount(2).setRange(2);
        var armor = (Armor)new Armor(15, 0).setName("Armor").setID("armor-0");

        var enemyEntity = new EnemyEntity(1, 1, 1).setRewardXP(10);
        var enemyController = new BerserkerEnemyController(enemyEntity);
        var enemyType = new EnemyType("enemyType-0", enemyController);
        enemy = new Enemy("enemy-0", enemyType);
        enemy.applyStats();
        enemy.getEntity().equip(armor);
        enemy.getEntity().equip(weapon);

        var playerEntity = new PlayerEntity(10, 1, 1);
        player = new Player("player-0", playerEntity);
        player.addEventListeners(new IEventListener() {
            public void run(EventArgument arg, Event e){}
        }, new IEventListener() {
            public void run(EventArgument arg, Event e){}
        });
        player.addToInventory(armor);
        player.addToInventory(weapon);
        player.equip(armor);
        player.equip(weapon);
    }

    @Test (expected = DefaultDiceNotSetException.class)
    public void testNoDefaultDice() throws Exception{
        enemy.getEntity().getArmor().setArmorClass(0);
        player.getEntity().getWeapon().setAttackModifier(1);

        player.getEntity().getWeapon().attack(enemy.getEntity().getArmorClass(), 2);
    }

    @Test
    public void testAttack() throws Exception{
        DiceRoller.getInstance().setDefault(20);
        player.getEntity().getWeapon().setAttackModifier(20);
        enemy.getEntity().getArmor().setArmorClass(0);
        enemy.getEntity().setLevel(1);

        boolean attackResult = player.attack(enemy.getEntity().getArmorClass(), 2);

        Assert.assertTrue(attackResult);
    }

    @Test
    public void testDamage() throws  Exception{
        enemy.getEntity().setHealth(1);
        enemy.applyStats();

        enemy.takeDamage(player.damage(2));
        boolean enemyDied = enemy.isDead();

        Assert.assertTrue(enemyDied);
    }

    @Test (expected = ItemNotInInventoryException.class)
    public void testNotInInventory() throws Exception{
        var newArmor = (Armor)new Armor(10, 1).setID("armor-1");        
        player.equip(newArmor);
    }

    @Test
    public void testInventorySize() throws Exception{
        var inventory = new Inventory(false);
        var simpleItem = (Item)new Item().setID("simple-item-0");
        var consumable = (Consumable)new Consumable("consumable-0", ModifierType.ATTACK, 1).setID("consumable-0");
        var equipment = (Armor)new Armor(10, 1).setID("armor-2");

        inventory.add(simpleItem);
        inventory.add(consumable);
        inventory.add(equipment);

        Assert.assertEquals(3, inventory.size());
    }

    @Test
    public void testEntityArmorClass() throws Exception{
        enemy.getEntity().getArmor().setArmorClass(1);
        enemy.getEntity().setLevel(1);

        Assert.assertEquals(enemy.getEntity().getArmorClass(), 2);
    }

    @Test
    public void testPlayerLevelUp() throws Exception{
        player.setRequiredXP(2);
        player.addXP(3);

        Assert.assertEquals(player.getEntity().getLevel(), 2);
        Assert.assertEquals(player.getXP(), 1);
    }

    @Test
    public void testActiveEnemyStorage() throws Exception{
        ActiveEnemyStorage.getInstance().add(enemy.getID(), enemy);
        var searchedEnemy = ActiveEnemyStorage.getInstance().get(enemy.getID());

        Assert.assertNotEquals(searchedEnemy, null);
    }

    @Test
    public void testPlayerEvents() throws Exception{
        playerDied = false;
        playerLeveledUp = 1;
        player.addEventListeners(new IEventListener() {
            public void run(EventArgument arg, Event e){
                playerLeveledUp++;
            }
        }, new IEventListener() {
            public void run(EventArgument arg, Event e){
                playerDied = true;
            }
        });

        player.levelUp();
        player.die();

        Assert.assertEquals(playerLeveledUp, 2);
        Assert.assertTrue(playerDied);
    }

    @Test
    public void testGridDistanceCalculation() throws Exception{
        var src = new GridPosition(0, 0);
        var dst = new GridPosition(0,1);

        double result = GridPosition.calculateAbsoluteDistance(src, dst);

        Assert.assertEquals(result, 1, 0);
    }
}