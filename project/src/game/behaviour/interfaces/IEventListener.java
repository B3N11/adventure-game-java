package game.behaviour.interfaces;

import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Event;

public interface IEventListener{
    public void run(Event event) throws InvalidArgumentException;
}
