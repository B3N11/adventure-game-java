package game.utility.dataclass;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import file.elements.EnemyMapData;
import file.elements.ItemMapData;
import game.utility.general.Identity;
import ui.data.GridPosition;

public class MapLayoutData extends Identity{
    
    private int horizontal;
    private int vertical;
    private String backgroundFilePath;
    private ArrayList<EnemyMapData> enemies;
    private ArrayList<ItemMapData> items;
    private GridPosition playerStartPosition;

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

    public void addEnemy(EnemyMapData enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();
        enemies.add(enemy);
    }

    public void addItem(ItemMapData item) throws ArgumentNullException{
        if(item == null)
            throw new ArgumentNullException();
        items.add(item);
    }
}