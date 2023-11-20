package game.logic.event;

import java.util.ArrayList;

import exception.general.ArgumentNullException;

public class Event{
 
    private EventArgument eventArgument;
    private ArrayList<IEventListener> eventListeners;
    private boolean removeOnRun;

    public Event(EventArgument object){
        eventListeners = new ArrayList<IEventListener>();
        removeOnRun = false;
        this.eventArgument = object;
    }

    public EventArgument getArgument() { return eventArgument; }

    public Event setArgument(EventArgument argument){
        eventArgument = argument;
        return this;
    }

    public final boolean isRemovingOnRun(){ return removeOnRun; }

    public final void setRemoveOnRun(boolean removeOnRun){
        this.removeOnRun = removeOnRun;
    }

    public final void addEventListener(IEventListener listener) throws ArgumentNullException{
        if(listener == null)
            throw new ArgumentNullException();
        
        eventListeners.add(listener);
    }

    public final void removeEventListener(IEventListener listener){
        eventListeners.remove(listener);
    }

    public final void triggerEvent() throws Exception{
        var iterator = eventListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().run(eventArgument, this);

            if(removeOnRun)
                iterator.remove();
        }
    }
}
