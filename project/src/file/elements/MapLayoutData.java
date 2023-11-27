package file.elements;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import game.utility.Identity;
import ui.data.GridPosition;

/**
 * This class represents the layout data for a map in the game.
 * It extends the Identity class.
 * 
 * The class contains the following fields:
 * - horizontal: The number of tiles horizontally.
 * - vertical: The number of tiles vertically.
 * - backgroundFilePath: The file path to the background image for the map.
 * - enemies: A list of enemy data for the map.
 * - items: A list of item data for the map.
 * - playerStartPosition: The starting position of the player on the map.
 * 
 * The class provides a constructor that initializes these fields and methods to get and set these fields.
 */
public class MapLayoutData extends Identity{
    
    private int horizontal;
    private int vertical;
    private String backgroundFilePath;
    private ArrayList<EnemyMapData> enemies;
    private ArrayList<ItemMapData> items;
    private GridPosition playerStartPosition;

    /**
     * Constructor for the MapLayoutData class.
     * Initializes the id, horizontal size, vertical size, background file path, and player start position.
     * @param id The id of the map layout data.
     * @param horizontal The number of tiles horizontally.
     * @param vertical The number of tiles vertically.
     * @param file The file path to the background image for the map.
     * @param playerPosition The starting position of the player on the map.
     * @throws ArgumentNullException if the id, file, or playerPosition is null.
     */
    public MapLayoutData(String id, int horizontal, int vertical, String file, GridPosition playerPosition) throws ArgumentNullException{        
        setID(id);
        this.horizontal = horizontal;
        this.vertical = vertical;
        backgroundFilePath = file;
        enemies = new ArrayList<EnemyMapData>();
        items = new ArrayList<ItemMapData>();
        this.playerStartPosition = playerPosition;
    }

    public int getHorizontal() { return horizontal; }
    public int getVertical() { return vertical; }
    public String getBackgroundFilePath() { return backgroundFilePath; }
    public ArrayList<EnemyMapData> getEnemies() { return enemies; }
    public ArrayList<ItemMapData> getItems() { return items; }
    public GridPosition getPlayerPosition() { return playerStartPosition; }    

    public void setBackgroundFilePath(String path) throws ArgumentNullException{
        if(path == null)
            throw new ArgumentNullException();
        backgroundFilePath = path;
    }

    /**
     * Adds an enemy to the map.
     * @param enemy The enemy data to add.
     * @throws ArgumentNullException if the enemy data is null.
     */
    public void addEnemy(EnemyMapData enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();
        enemies.add(enemy);
    }

    /**
     * Adds an item to the map.
     * @param item The item data to add.
     * @throws ArgumentNullException if the item data is null.
     */
    public void addItem(ItemMapData item) throws ArgumentNullException{
        if(item == null)
            throw new ArgumentNullException();
        items.add(item);
    }
}