package game.global;

import game.behaviour.entities.enemy.EnemyType;

public class EnemyTypeStorage extends Storage<EnemyType>{
    
    private static EnemyTypeStorage instance;

    private EnemyTypeStorage(){}

    public static EnemyTypeStorage getInstance(){
        if(instance == null)
            instance = new EnemyTypeStorage();
        return instance;
    }
}