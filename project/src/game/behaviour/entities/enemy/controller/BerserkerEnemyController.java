package game.behaviour.entities.enemy.controller;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

public class BerserkerEnemyController extends EnemyBehaviourController{

    public BerserkerEnemyController(EnemyEntity enemy) throws ArgumentNullException {
        super(enemy);
        
        controllerType = EnemyBehaviourControllerType.BERSERK;
    }
    
}
