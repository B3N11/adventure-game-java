package game.behaviour.abstracts;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import game.behaviour.interfaces.IEventListener;
import game.utility.general.Identifiable;

public abstract class Event extends Identifiable{
 
    protected ArrayList<IEventListener> eventListeners;
    protected boolean removeOnRun;

    public Event(){
        eventListeners = new ArrayList<IEventListener>();
        removeOnRun = false;
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

    protected final void triggerEvent() throws Exception{
        var iterator = eventListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().run(this);

            if(removeOnRun)
                iterator.remove();
        }
    }
}
