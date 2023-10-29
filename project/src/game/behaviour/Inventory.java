package game.behaviour;

import java.util.HashMap;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Event;
import game.behaviour.interfaces.IEventListener;
import game.enums.ModifierType;

public class Inventory implements IEventListener{
    
    private HashMap<String, Equipment> equipments;
    private HashMap<String, Consumable> consumables;

    public Inventory(){
        equipments = new HashMap<String, Equipment>();
        consumables = new HashMap<String, Consumable>();
    }

    public void add(Equipment equipment) throws ArgumentNullException{
        if(equipment == null)
            throw new ArgumentNullException();
        
        equipments.put(equipment.getID(), equipment);
    }

    public void add(Consumable consumable) throws ArgumentNullException{
        if(consumable == null)
            throw new ArgumentNullException();
        
        consumables.put(consumable.getID(), consumable);
    }

    public boolean contains(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        return equipments.containsKey(id) || consumables.containsKey(id);
    }

    public double calculateModifiers(ModifierType type){
        double result = 0;

        for(var consumable : consumables.entrySet())
            if(consumable.getValue().getType() == type)
                result += consumable.getValue().use();

        return result;
    }

    @Override
    public void run(Event event) throws InvalidArgumentException{
        if(!(event instanceof Consumable))
            throw new InvalidArgumentException();
        
        var consumable = (Consumable)event;
    }
}
