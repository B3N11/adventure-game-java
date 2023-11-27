package game.behaviour.entities.enemy.controller;

import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.IInteractiveEntity;
import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

/**
 * This class represents a BerserkerEnemyController in the game.
 * It extends the EnemyBehaviourController class and overrides the runEnemy method.
 * 
 * The class contains the following fields:
 * - controllerType: The type of the controller, set to BERSERK in the constructor.
 * 
 * The class provides a constructor that initializes the enemyEntity and sets up the events.
 */
public class BerserkerEnemyController extends EnemyBehaviourController {

    public BerserkerEnemyController(EnemyEntity enemy) throws ArgumentNullException {
        super(enemy);
        
        controllerType = EnemyBehaviourControllerType.BERSERK;
    }
    
    /**
     * Executes the berserker enemy's behaviour towards a target.
     * The berserker enemy attempts to attack the target and if successful, deals damage to the target.
     * @param target The target of the enemy's behaviour.
     * @param distance The distance to the target.
     * @throws Exception if an error occurs during the execution of the behaviour.
     */
    @Override
    public void runEnemy(IInteractiveEntity target, double distance) throws Exception{

        String hitAttempt = enemyEntity.getName() + ":";
        String damageNumber = enemyEntity.getName() + ":";
        try{
            boolean attackEnemy = enemyEntity.attack(target.getEntity().getArmorClass(), distance);

            if(!attackEnemy){
                hitAttempt += "FAIL";
                attemptedToHitEvent.getArgument().setArgument(hitAttempt);
                attemptedToHitEvent.triggerEvent();
                throw new UnsupportedOperationException();  //Break try
            }

            hitAttempt += "SUCCESS";
            attemptedToHitEvent.getArgument().setArgument(hitAttempt);
            attemptedToHitEvent.triggerEvent();

            int damage = enemyEntity.damage(distance);
            target.takeDamage(damage);

            damageNumber += damage + ":" + target.getCurrentHealth() + "/" + target.getEntity().getHealth();
            damageEvent.getArgument().setArgument(damageNumber);
            damageEvent.triggerEvent();
        }
        catch(NoWeaponEquippedException e){
            hitAttempt += "FAIL";
            attemptedToHitEvent.triggerEvent();
        }catch(UnsupportedOperationException e){}
    }
}
