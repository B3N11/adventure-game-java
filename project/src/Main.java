import java.awt.Dimension;
import javax.swing.*;

import exception.dice.InvalidDiceSideCountException;
import exception.entity.ItemNotInInventoryException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import file.FileIOUtil;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.Player;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.enemy.EnemyType;
import game.behaviour.entities.enemy.controller.BerserkerEnemyController;
import game.behaviour.entities.enemy.controller.RangerEnemyController;
import game.behaviour.weapons.Shotgun;
import game.global.GameHandler;
import game.global.storage.EnemyTypeStorage;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.delegates.GenericDelegate;
import game.utility.dice.DiceRoller;
import ui.data.GridPosition;
import ui.elements.CombatFrame;
import ui.elements.GridEntityComponent;
import ui.elements.PlayFrame;
import ui.elements.PlayfieldPanel;
import ui.elements.UtilityButtonPanel;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;
import uilogic.UIHandler;

public class Main {

    public static void main(String[] args) throws Exception{
        //GameHandler.getInstance().start();
        test();
    }

    static void createTestData(){
        var fileIO = new FileIOUtil();
        
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
        var enemyType = new EnemyType("shotgun-thug", enemyController, "resources/img/characters/7.png");
        var enemy = new Enemy("shotgun-thug-I-001", enemyType);
        enemy.setPosition(new GridPosition(1,3));

        enemyController.addEventListeners(new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String arg = argument.getArgument().toString();
                UIHandler.getInstance().addToCombatLog(arg);
            }
        }, new IEventListener() {
            public void run(EventArgument argument, Event trigger){
                String arg = argument.getArgument().toString();
                UIHandler.getInstance().addToCombatLog(arg);
            }
        });

        UIHandler.getInstance().getPlayFieldHandler().placeEntity(enemy, enemyType.getIconFilePath());

        var player = new Player(100, 5, 2);
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