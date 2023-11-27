package game.utility.event;

/**
 * This interface represents a listener of an event in the game.
 * It contains a method to run the listener with an event argument and the triggered event.
 */
public interface IEventListener{
    /**
     * Runs the listener with the specified event argument and triggered event.
     * @param object The argument of the event.
     * @param triggeredEvent The triggered event.
     * @throws Exception if the listener throws an exception.
     */
    public void run(EventArgument object, Event triggeredEvent) throws Exception;
}
