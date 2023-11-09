package file.elements;

import java.io.Serializable;
import java.util.ArrayList;

import exception.general.ArgumentNullException;
import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

public class EnemyTypeSave implements Serializable{
    
    private String enemyTypeID;
    private EnemyEntity entity;
    private EnemyBehaviourControllerType controllerType;
    private String iconFilePath;

    private String enemyArmorID;
    private String enemyWeaponID;
    private ArrayList<String> inventory;

    public EnemyTypeSave(String enemyTypeID) throws ArgumentNullException{
        if(enemyTypeID == null)
            throw new ArgumentNullException();
        
        this.enemyTypeID = enemyTypeID;
        inventory = new ArrayList<String>();
    }

    public String getEnemyTypeID() { return enemyTypeID; }
    public EnemyEntity getEntity() { return entity;}
    public EnemyBehaviourControllerType getControllerType() { return controllerType; }
    public String getArmorID() { return enemyArmorID; }
    public String getWeaponID() { return enemyWeaponID; }
    public ArrayList<String> getInventory() { return inventory; }
    public String getIconFilePath() { return iconFilePath; }

    public void setIconFilePath(String path) throws ArgumentNullException{
        if(path == null)
            throw new ArgumentNullException();
        iconFilePath = path;
    }

    public void setEntity(EnemyEntity entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();

        this.entity = entity;
    }

    public void setControllerType(EnemyBehaviourControllerType type){
        controllerType = type;
    }

    public void addToInventory(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        inventory.add(id);
    }

    public void setArmorID(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        enemyArmorID = id;
    }

    public void setWeaponID(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        enemyWeaponID = id;
    }
}