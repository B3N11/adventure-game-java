package game.behaviour.interfaces;

import game.behaviour.abstracts.Event;

public interface IEventListener{
    public void run(Event event) throws Exception;
}
