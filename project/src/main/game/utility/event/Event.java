package main.game.utility.event;

import java.util.ArrayList;

import main.exception.general.ArgumentNullException;

/**
 * This class represents an event in the game.
 * It contains an event argument and a list of event listeners, and can be set to remove on run.
 * 
 * The class contains the following fields:
 * - eventArgument: The argument of the event.
 * - eventListeners: The list of listeners of the event.
 * - removeOnRun: Whether the event is removed on run.
 */
public class Event{
 
    private EventArgument eventArgument;
    private ArrayList<IEventListener> eventListeners;
    private boolean removeOnRun;

    /**
     * Constructor for the Event class.
     * Initializes the event with the specified argument, an empty list of listeners, and a remove on run status of false.
     * @param object The argument of the event.
     */
    public Event(EventArgument object){
        eventListeners = new ArrayList<IEventListener>();
        removeOnRun = false;
        this.eventArgument = object;
    }

    public EventArgument getArgument() { return eventArgument; }

    /**
     * Sets the argument of the event.
     * @param argument The new argument to set.
     * @return The event itself, for chaining.
     */
    public Event setArgument(EventArgument argument){
        eventArgument = argument;
        return this;
    }

    /**
     * Checks whether the event is removed on run.
     * @return Whether the event is removed on run.
     */
    public final boolean isRemovingOnRun(){ return removeOnRun; }

    /**
     * Sets whether the event is removed on run.
     * @param removeOnRun The new remove on run status to set.
     */
    public final void setRemoveOnRun(boolean removeOnRun){
        this.removeOnRun = removeOnRun;
    }

    /**
     * Adds a listener to the event. It will be triggered when the event is triggered.
     * @param listener The listener to add.
     * @throws ArgumentNullException if the listener is null.
     */
    public final void addEventListener(IEventListener listener) throws ArgumentNullException{
        if(listener == null)
            throw new ArgumentNullException();
        
        eventListeners.add(listener);
    }

    /**
     * Removes a listener from the event.
     * @param listener The listener to remove.
     */
    public final void removeEventListener(IEventListener listener){
        eventListeners.remove(listener);
    }

    /**
     * Triggers the event.
     * Each listener of the event is run with the event argument and the event itself.
     * If the event is set to remove on run, the listener is removed after it is run.
     * @throws Exception if any listener throws an exception.
     */
    public final void triggerEvent() throws Exception{
        var iterator = eventListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().run(eventArgument, this);

            if(removeOnRun)
                iterator.remove();
        }
    }
}