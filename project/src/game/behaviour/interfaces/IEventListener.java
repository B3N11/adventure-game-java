package game.behaviour.interfaces;

import game.behaviour.abstracts.Event;

public interface IEventListener{
    public void run(Object object, Event triggeredEvent) throws Exception;
}
