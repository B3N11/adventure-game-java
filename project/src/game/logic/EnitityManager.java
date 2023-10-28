package game.logic;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;
import exception.entity.AlreadyAttackedThisTurnException;
import exception.entity.NoWeaponEquippedException;
import game.behaviour.abstracts.Entity;

public class EnitityManager {
    
    private Entity entity;

    private boolean attackedThisTurn;

    public Entity getEntity(){ return entity; }

    public boolean attack(int targetAC, int distance) throws DefaultDiceNotSetException, AlreadyAttackedThisTurnException, NoWeaponEquippedException{
        if(attackedThisTurn)
            throw new AlreadyAttackedThisTurnException();
        
        attackedThisTurn = true;
        return entity.attack(targetAC, distance);
    }

    public int damage(int distance) throws InvalidDiceSideCountException, NoWeaponEquippedException{
        return entity.damage(distance);
    }
}