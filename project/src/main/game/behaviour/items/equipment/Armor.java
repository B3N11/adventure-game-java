package main.game.behaviour.items.equipment;

import main.game.behaviour.abstracts.Equipment;
import main.game.enums.EquipmentType;

/**
 * This class represents an Armor in the game.
 * It extends the Equipment class and includes additional properties such as armor class and movement bonus.
 * 
 * The class contains the following fields:
 * - armorClass: The armor class of the armor.
 * - movementBonus: The movement bonus provided by the armor.
 * 
 * The class provides getter and setter methods for these fields.
 * It also overrides the getDisplayInfo and getStatistics methods from the Equipment class.
 */
public class Armor extends Equipment {
 
    protected int armorClass;
    protected int movementBonus;

    public Armor(int armorClass, int movementBonus){
        setArmorClass(armorClass);
        setMovementBonus(movementBonus);

        equipmentType = EquipmentType.ARMOR;
    }

    public int getArmorClass(){ return armorClass; }
    public int getMovementBonus(){ return movementBonus; }

    public void setArmorClass(int armorClass){
        this.armorClass = armorClass;
    }

    public void setMovementBonus(int movementBonus){
        this.movementBonus = movementBonus;
    }

    @Override
    public String getDisplayInfo() {
        return getDescription();
    }

    @Override
    public String getStatistics(int bearerLevel) {
        String result = "Armor Class: " + armorClass + " + " + bearerLevel + "\n" + "Movement Bonus: " + movementBonus;
        return result;
    }
}