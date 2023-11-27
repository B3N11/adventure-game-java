package file.elements;

import java.io.Serializable;
import java.util.ArrayList;

import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

/**
 * This class represents the saved data of an enemy type.
 * It includes the enemy's type ID, entity, behavior controller type, icon file path, armor ID, weapon ID, and inventory.
 */
public class EnemyTypeSave implements Serializable{
    
    public String enemyTypeID;
    public EnemyEntity entity;
    public EnemyBehaviourControllerType controllerType;
    public String iconFilePath;

    public String enemyArmorID;
    public String enemyWeaponID;
    public ArrayList<String> inventory;
}