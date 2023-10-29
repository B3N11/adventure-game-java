package game.behaviour;

import java.util.HashMap;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Event;
import game.behaviour.interfaces.IEventListener;
import game.enums.ModifierType;
import game.logic.InventoryMarker;

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

    @Override
    public void run(Event event) throws Exception{
        if(!(event instanceof Consumable))
            throw new InvalidArgumentException();
        
        var consumable = (Consumable)event;
        
        if(!removeOnRanOut){
            if(event.isRemovingOnRun())
                event.addEventListener(this);
            return;
        }

        if(!event.isRemovingOnRun())
            event.removeEventListener(this);
        
        //Mark it for removal, so iterator can remove it in calculateModifiers
        consumables.get(consumable.getID()).mark(true);
    }
}