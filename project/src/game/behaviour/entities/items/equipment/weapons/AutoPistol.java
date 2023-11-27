package game.behaviour.entities.items.equipment.weapons;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Weapon;
import game.enums.WeaponType;

/**
 * This class represents an AutoPistol in the game.
 * It extends the Weapon class.
 * 
 * The class contains a constructor that initializes the id and name of the weapon and sets the weapon type to AUTOPISTOL and the number of attacks in a round to 2.
 */
public class AutoPistol extends Weapon{

    /**
     * Constructor for the AutoPistol class.
     * Initializes the id and name of the weapon and sets the weapon type to AUTOPISTOL and the number of attacks in a round to 2.
     * @param id The id of the weapon.
     * @param weaponName The name of the weapon.
     * @throws ArgumentNullException if the id or weaponName is null.
     */
    public AutoPistol(String id, String weaponName) throws ArgumentNullException {
        super(id, weaponName);
        
        weaponType = WeaponType.AUTOPISTOL;
        try{ setAttacksInRound(2); }
        catch(InvalidArgumentException e){}
    }    
}
