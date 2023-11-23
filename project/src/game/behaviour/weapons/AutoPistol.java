package game.behaviour.weapons;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Weapon;
import game.enums.WeaponType;

public class AutoPistol extends Weapon{

    public AutoPistol(String id, String weaponName) throws ArgumentNullException {
        super(id, weaponName);
        
        weaponType = WeaponType.AUTOPISTOL;
        try{ setAttacksInRound(2); }
        catch(InvalidArgumentException e){}
    }    
}
