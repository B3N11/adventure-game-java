package game.behaviour.abstracts;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import game.behaviour.interfaces.IEventListener;

public class Event{
 
    private Object object;
    private ArrayList<IEventListener> eventListeners;
    private boolean removeOnRun;

    public Event(Object object){
        eventListeners = new ArrayList<IEventListener>();
        removeOnRun = false;
        this.object = object;
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
            iterator.next().run(object, this);

            if(removeOnRun)
                iterator.remove();
        }
    }
}
