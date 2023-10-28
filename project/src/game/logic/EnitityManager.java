package game.logic;

import game.behaviour.abstracts.Entity;

public class EnitityManager {
    
    private Entity entity;

    private double currentMovement;
    private boolean attackedThisTurn;

    public boolean move(double distance){
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        return true;
    }
}