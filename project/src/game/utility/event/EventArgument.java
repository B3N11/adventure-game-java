package game.utility.event;

import exception.general.ArgumentNullException;

public class EventArgument <T>{

    private T argument;

    public T getArgument() { return argument; }

    public EventArgument<T> setArgument(T argument) throws ArgumentNullException{
        if(argument == null)
            throw new ArgumentNullException();
        this.argument = argument;
        return this;
    }
}