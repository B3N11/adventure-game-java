package game.global.storage;

import game.behaviour.entities.enemy.Enemy;

public class ActiveEnemyStorage extends Storage<Enemy>{
    
    private static ActiveEnemyStorage instance;

    private ActiveEnemyStorage(){}

    public static ActiveEnemyStorage getInstance(){
        if(instance == null)
            instance = new ActiveEnemyStorage();
        return instance;
    }
}
