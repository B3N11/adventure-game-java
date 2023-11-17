package game.utility.dataclass;

import java.util.ArrayList;

import exception.general.ArgumentNullException;
import file.elements.EnemyMapData;
import game.utility.general.Identity;
import ui.data.GridPosition;

public class MapLayoutData extends Identity{
    
    private int horizontal;
    private int vertical;
    private String backgroundFilePath;
    private ArrayList<EnemyMapData> enemies;
    private GridPosition playerStartPosition;

    public MapLayoutData(String id, int horizontal, int vertical, String file, GridPosition playerPosition) throws ArgumentNullException{        
        setID(id);
        this.horizontal = horizontal;
        this.vertical = vertical;
        backgroundFilePath = file;
        enemies = new ArrayList<EnemyMapData>();
        this.playerStartPosition = playerPosition;
    }

    public int getHorizontal() { return horizontal; }
    public int getVertical() { return vertical; }
    public String getBackgroundFilePath() { return backgroundFilePath; }
    public ArrayList<EnemyMapData> getEnemies() { return enemies; }
    public GridPosition getPlayerPosition() { return playerStartPosition; }    

    public void addEnemy(EnemyMapData enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        enemies.add(enemy);
    }
}