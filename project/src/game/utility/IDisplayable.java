package game.utility;

/**
 * This interface represents a displayable item in the game.
 * It contains methods to get the name, display information, and statistics of the object.
 */
public interface IDisplayable {   
    
    /**
     * Gets the name of the object.
     * @return The name of the object.
     */
    public String getName();
    
    /**
     * Gets the display information of the object.
     * @return The display information of the object.
     */
    public String getDisplayInfo();

    /**
     * Gets the statistics of the object.
     * @param bearerLevel The level of the bearer of the item.
     * @return The statistics of the object.
     */
    public String getStatistics(int bearerLevel);
}