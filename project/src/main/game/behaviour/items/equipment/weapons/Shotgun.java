package main.game.behaviour.items.equipment.weapons;

import main.exception.general.ArgumentNullException;
import main.exception.general.InvalidArgumentException;
import main.exception.dice.DefaultDiceNotSetException;
import main.game.behaviour.abstracts.Weapon;
import main.game.enums.WeaponType;

/**
 * This class represents a Shotgun in the game.
 * It extends the Weapon class.
 * 
 * The class contains the following fields:
 * - hitRange: The hit range of the shotgun in which the shotgun will always hit the target.
 * 
 * The class provides a constructor that initializes the id, name, and hit range of the shotgun and methods to get and set the hit range and to attack a target.
 */
public class Shotgun extends Weapon {

    private double hitRange;
    
    /**
     * Constructor for the Shotgun class.
     * Initializes the id, name, and hit range of the shotgun and sets the weapon type to SHOTGUN.
     * @param id The id of the shotgun.
     * @param weaponName The name of the shotgun.
     * @param hitRange The hit range of the shotgun.
     * @throws ArgumentNullException if the id or weaponName is null.
     * @throws InvalidArgumentException if the hitRange is less than 0 or greater than 1.
     */
    public Shotgun(String id, String weaponName, double hitRange) throws ArgumentNullException, InvalidArgumentException{
        super(id, weaponName);
        weaponType = WeaponType.SHOTGUN;

        setHitRange(hitRange);
    }
    
    public double getHitRange(){ return hitRange; }

    public Shotgun setHitRange(double newHitRange) throws InvalidArgumentException{
        if(newHitRange < 0 || newHitRange > 1)
            throw new InvalidArgumentException();
        
        hitRange = newHitRange;
        return this;
    }

    /**
     * Attacks a target.
     * If the distance is less than or equal to the range times the hit range, the attack is guaranteed to hit.
     * @param targetAC The armor class of the target.
     * @param distance The distance to the target.
     * @return True if the attack hits, false otherwise.
     * @throws DefaultDiceNotSetException if the default dice is not set.
     */
    @Override
    public boolean attack(int targetAC, double distance) throws DefaultDiceNotSetException{

        //Shotgun customisation: Garanteed Hit
        if(distance <= (range * hitRange))
            return true;

        return super.attack(targetAC, distance);
    }
}