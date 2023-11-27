package main.game.utility;

/**
 * This interface represents a generic delegate in the game. It is used to pass a callback with generic parameters.
 * It contains a single method, run, that takes an object as a parameter.
 */
public interface GenericDelegate {
    /**
     * Runs the delegate with the specified object.
     * @param o The object to run the delegate with.
     */
    public void run(Object o);
}