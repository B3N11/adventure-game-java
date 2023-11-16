package game.behaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Item;
import game.enums.ModifierType;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.general.InventoryMarker;

public class Inventory implements IEventListener{
    
    private boolean removeOnRanOut;

    private HashMap<String, Equipment> equipments;
    private HashMap<String, InventoryMarker<Consumable>> consumables;

    public Inventory(boolean removeWhenRanOut){
        this.removeOnRanOut = removeWhenRanOut;
        equipments = new HashMap<String, Equipment>();
        consumables = new HashMap<String, InventoryMarker<Consumable>>();
    }

    public void setRemoveOnRanOut(boolean remove){
        removeOnRanOut = remove;
    }

    public void add(Equipment equipment) throws ArgumentNullException{
        if(equipment == null)
            throw new ArgumentNullException();
        
        equipments.put(equipment.getID(), equipment);
    }

    public void add(Consumable consumable) throws ArgumentNullException{
        if(consumable == null)
            throw new ArgumentNullException();
        
        try{
            //Won't happen, as consumable is always an accepted type
            consumables.put(consumable.getID(), new InventoryMarker<Consumable>(consumable));
        }catch(InvalidArgumentException e){}
    }

    public Item remove(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        var resultConsumable = consumables.remove(id);
        if(resultConsumable != null)
            return resultConsumable.getItem();
        
        var resultEquipment = equipments.remove(id);
        return resultEquipment;
    }

    public boolean contains(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        return equipments.containsKey(id) || consumables.containsKey(id);
    }

    public double calculateModifiers(ModifierType type) throws Exception{
        double result = 0;

        var iterator = consumables.entrySet().iterator();
        while (iterator.hasNext()) {
            var consumable = iterator.next().getValue();

            if(consumable.getItem().getType() != type)
                continue;

            result += consumable.getItem().use();

            //If consumable event callback (run()) marked it as removable, remove it
            if(consumable.isMarked())
                iterator.remove();
        }

        return result;
    }

    public List<Consumable> getConsumables(){
        var result = new ArrayList<Consumable>();

        for(var consumable : consumables.entrySet())
            result.add(consumable.getValue().getItem());

        return Collections.unmodifiableList(result);
    }

    public List<Equipment> getEquipments(){
        var result = new ArrayList<Equipment>();

        for(var equipment : equipments.entrySet())
            result.add(equipment.getValue());

        return Collections.unmodifiableList(result);
    }

    @Override
    public void run(EventArgument object, Event triggeredEvent) throws Exception{
        if(!(object.getArgument() instanceof Consumable))
            throw new InvalidArgumentException();
        
        var consumable = (Consumable)object.getArgument();
        
        if(!removeOnRanOut){
            if(triggeredEvent.isRemovingOnRun())
                triggeredEvent.addEventListener(this);
            return;
        }

        if(!triggeredEvent.isRemovingOnRun())
            triggeredEvent.removeEventListener(this);
        
        //Mark it for removal, so iterator can remove it in calculateModifiers
        consumables.get(consumable.getID()).mark(true);
    }
}