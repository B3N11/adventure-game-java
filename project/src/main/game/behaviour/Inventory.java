package main.game.behaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import main.exception.general.ArgumentNullException;
import main.exception.general.InvalidArgumentException;
import main.game.behaviour.abstracts.Equipment;
import main.game.behaviour.items.Consumable;
import main.game.behaviour.items.Item;
import main.game.enums.ItemType;
import main.game.enums.ModifierType;
import main.game.utility.event.Event;
import main.game.utility.event.EventArgument;
import main.game.utility.event.IEventListener;

/**
 * This class represents an Inventory in the game.
 * It implements the IEventListener interface.
 * 
 * The class contains the following fields:
 * - removeOnRanOut: A flag indicating whether to remove an item when it runs out.
 * - equipments: A collection of equipment items.
 * - consumables: A collection of consumable items.
 * - simpleItems: A collection of simple items.
 * 
 * The class provides a constructor that initializes these fields and a method to get the size of the inventory.
 */
public class Inventory implements IEventListener{
    
    private boolean removeOnRanOut;

    private HashMap<String, Equipment> equipments;
    private HashMap<String, InventoryMarker<Consumable>> consumables;
    private HashMap<String, Item> simpleItems;

    /**
     * Constructor for the Inventory class.
     * Initializes the removeOnRanOut flag and the collections of items.
     * @param removeWhenRanOut The value to set the removeOnRanOut flag to.
     */
    public Inventory(boolean removeWhenRanOut){
        this.removeOnRanOut = removeWhenRanOut;
        equipments = new HashMap<String, Equipment>();
        consumables = new HashMap<String, InventoryMarker<Consumable>>();
        simpleItems = new HashMap<String, Item>();
    }

    /**
     * Gets the size of the inventory.
     * @return The size of the inventory. Size is the sum of the number of equipments, consumables, and simple items.
     */
    public int size(){
        return equipments.size() + consumables.size() + simpleItems.size();
    }

    public void setRemoveOnRanOut(boolean remove){
        removeOnRanOut = remove;
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to add.
     * @throws InvalidArgumentException if the item is null.
     */
    public void add(Item item) throws ArgumentNullException{
        if(item == null)
            throw new ArgumentNullException();

        if(item.getItemType() == ItemType.EQUIPMENT)
            equipments.put(item.getID(), (Equipment)item);
        else if(item.getItemType() == ItemType.CONSUMABLE){
            try{ consumables.put(item.getID(), new InventoryMarker<Consumable>((Consumable)item)); }
            catch(InvalidArgumentException e){}
        }
        else if(item.getItemType() == ItemType.SIMPLE)
            simpleItems.put(item.getID(), item);
    }

    /**
     * Removes an item from the inventory.
     * @param item The item to remove.
     * @throws InvalidArgumentException if the item is null.
     */
    public Item remove(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        if(consumables.containsKey(id)){
            var resultConsumable = consumables.remove(id);
            if(resultConsumable != null)
                return resultConsumable.getItem();
        }
        else if(equipments.containsKey(id)){
            return equipments.remove(id);
        }
        else if(simpleItems.containsKey(id)){
            return simpleItems.remove(id);
        }

        return null;
    }

    /**
     * Checks if the inventory contains a specific item.
     * @param item The item to check for.
     * @return True if the inventory contains the item, false otherwise.
     * @throws InvalidArgumentException if the item is null.
     */
    public boolean contains(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        return equipments.containsKey(id) || consumables.containsKey(id) || simpleItems.containsKey(id);
    }

    /**
     * Calculates the modifiers of the inventory.
     * @param bearerLevel The level of the bearer of the inventory.
     * @return The modifiers of the inventory.
     * @throws Exception if an error occurs during the calculation.
     */
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

    public List<Item> getSimpleItems(){
        var result = new ArrayList<Item>();

        for(var item : simpleItems.entrySet())
            result.add(item.getValue());
        
        return Collections.unmodifiableList(result);
    }

    /**
     * Gets all items in the inventory.
     * @return A list of all items in the inventory.
     */
    public List<Item> getAllItems(){
        var result = new ArrayList<Item>();

         for(var consumable : consumables.entrySet())
            result.add(consumable.getValue().getItem());
        for(var equipment : equipments.entrySet())
            result.add(equipment.getValue());
        for(var item : simpleItems.entrySet())
            result.add(item.getValue());
        
        return Collections.unmodifiableList(result);
    }

    /**
     * Runs an event when a consumable is out of charge.It removes the consumable from the inventory if the removeOnRanOut flag is set.
     * @param object The argument for the event.
     * @param triggeredEvent The event to run.
     * @throws Exception if an error occurs during the event.
     */
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