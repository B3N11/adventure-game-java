package uilogic;

import java.io.Serializable;
import java.util.ArrayList;

import exception.general.ArgumentNullException;
import file.elements.EnemyMapData;
import game.utility.general.Identifiable;

public class MapLayoutData extends Identifiable implements Serializable{
    
    private int horizontal;
    private int vertical;
    private String filePath;
    private ArrayList<EnemyMapData> enemies;

    public MapLayoutData(String id, int x, int y, String file) throws ArgumentNullException{        
        setID(id);
        horizontal = x;
        vertical = y;
        filePath = file;
        enemies = new ArrayList<EnemyMapData>();
    }

    public int getHorizontal() { return horizontal; }
    public int getVertical() { return vertical; }
    public String getFilePath() { return filePath; }
    public ArrayList<EnemyMapData> getEnemies() { return enemies; }

    public void addEnemy(EnemyMapData enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        enemies.add(enemy);
    }
}