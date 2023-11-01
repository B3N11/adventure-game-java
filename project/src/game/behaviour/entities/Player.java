package game.behaviour.entities;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Entity;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Weapon;
import game.enums.ModifierType;

public class Player extends Entity{
    
    //Progression
    private int xp;
    private int requiredXP;

    private Inventory inventory;

    public Player(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super("PLAYER", health, movement, level);

        inventory = new Inventory(false);
    }

    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }

    public int addXP(int newXP){
        int leftoverXP = newXP - (requiredXP - xp);

        if(leftoverXP >= 0)
            levelUp(leftoverXP);
        else
            xp += newXP;

        return level;
    }

    private void levelUp(int leftoverXP){
        levelUp();
        xp = leftoverXP;
    }

    public void levelUp(){
        level++;
        requiredXP = level * 150;
    }

    public void addToInventory(Equipment equipment) throws ArgumentNullException{
        inventory.add(equipment);
    }

    public void addToInventory(Consumable consumable) throws ArgumentNullException{
        inventory.add(consumable);
    }

    @Override
    public boolean attack(int targetAC, int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        int finalTargetAC = targetAC - (int)inventory.calculateModifiers(ModifierType.ATTACK);
        return weapon.attack(finalTargetAC, distance);
    }

    @Override
    public int damage(int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level + (int)inventory.calculateModifiers(ModifierType.DAMAGE);
    }

    @Override
    public void resetMovement() throws Exception{
        currentMovement = movement + armor.getMovementBonus() + inventory.calculateModifiers(ModifierType.MOVEMENT);
    }

    @Override
    public int getArmorClass() throws Exception{
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        result += (int)inventory.calculateModifiers(ModifierType.ARMOR_CLASS);
        return result;
    }

    @Override
    public void equip(Weapon weapon) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(weapon.getID()))
            throw new ItemNotInInventoryException();
        
        this.weapon = weapon;
    }

    @Override
    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(armor.getID()))
            throw new ItemNotInInventoryException();
        
        this.armor = armor;
    }
}
