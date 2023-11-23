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
import ui.elements.InteractiveGridPanel;
import ui.elements.UtilityButtonPanel;
import uilogic.GridButtonHandler;
import uilogic.MultipleButtonHandler;
import uilogic.UIHandler;

public class Main {

    public static void main(String[] args) throws Exception{
        //UIHandler.getInstance().displayPlayerDeath();
        DiceRoller.getInstance().setDefault(20);
        DiceRoller.getInstance().setDelegate(new GenericDelegate() {
            public void run(Object o){ UIHandler.getInstance().displayDiceRollResult((Integer)o); }
        });
        GameHandler.getInstance().start();
        //DataCreator.createTestData();
    }
}