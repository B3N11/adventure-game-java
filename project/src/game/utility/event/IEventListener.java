package game.utility.event;

public interface IEventListener{
    public void run(EventArgument object, Event triggeredEvent) throws Exception;
}
