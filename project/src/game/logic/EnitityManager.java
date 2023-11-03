package game.logic;

import exception.entity.AlreadyAttackedThisTurnException;
import exception.general.ArgumentNullException;
import game.behaviour.abstracts.Entity;

public class EnitityManager {
    
    /* private Entity entity;
    private boolean attackedThisTurn;
    private int rolledInitiative;

    public EnitityManager(Entity entity) throws ArgumentNullException{
        if(entity == null)
            throw new ArgumentNullException();

        attackedThisTurn = false;
        this.entity = entity;
        rolledInitiative = 0;
    }

    public Entity getEntity(){ return entity; }

    public boolean attack(int targetAC, int distance) throws Exception{
        if(attackedThisTurn)
            throw new AlreadyAttackedThisTurnException();
        
        attackedThisTurn = true;
        return entity.attack(targetAC, distance);
    }

    public int damage(int distance) throws Exception{
        return entity.damage(distance);
    }

    public void resetTurn() throws Exception{
        attackedThisTurn = false;
        entity.resetMovement();
    }

    public int getRolledInitiative(){ return rolledInitiative; } */
}