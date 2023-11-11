package game.behaviour.entities.enemy.controller;

import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EnemyBehaviourControllerType;

public class BerserkerEnemyController extends EnemyBehaviourController {

    public BerserkerEnemyController(EnemyEntity enemy) throws ArgumentNullException {
        super(enemy);
        
        controllerType = EnemyBehaviourControllerType.BERSERK;
    }
    
    @Override
    public void runEnemy(IInteractiveEntity target, double distance) throws Exception{

        String hitAttempt = enemyEntity.getName() + " attacked " + target.getEntity().getName() + " from distance of " + distance + ".\nResult: ";
        String damageNumber = enemyEntity.getName() + " damaged " + target.getEntity().getName() + " for ";
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

            damageNumber += damage + " point.";
            damageEvent.getArgument().setArgument(damageNumber);
            damageEvent.triggerEvent();
        }
        catch(NoWeaponEquippedException e){
            hitAttempt += "FAIL";
            attemptedToHitEvent.triggerEvent();
        }catch(UnsupportedOperationException e){}
    }
}
