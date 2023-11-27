package game.utility.event;

import exception.general.ArgumentNullException;

/**
 * This class represents an argument of an event in the game.
 * It contains a generic argument and methods to get and set the argument.
 * 
 * The class contains the following field:
 * - argument: The argument of the event.
 */
public class EventArgument <T>{

    private T argument;

    public T getArgument() { return argument; }

    /**
     * Sets the argument of the event.
     * @param argument The new argument to set.
     * @return The event argument itself, for chaining.
     * @throws ArgumentNullException if the new argument is null.
     */
    public EventArgument<T> setArgument(T argument) throws ArgumentNullException{
        if(argument == null)
            throw new ArgumentNullException();
        this.argument = argument;
        return this;
    }
}