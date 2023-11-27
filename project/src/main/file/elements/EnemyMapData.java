package main.file.elements;

import java.io.Serializable;

import main.exception.general.ArgumentNullException;
import main.uilogic.GridPosition;

/**
 * This class represents the data of an enemy on the map.
 * It includes the asset ID, instance ID, and position of the enemy.
 */
public class EnemyMapData implements Serializable{
    
    private String assetID;
    private String instanceID;
    private GridPosition position;

    public EnemyMapData(String assetID, String instanceID) throws ArgumentNullException{
        if(assetID == null || instanceID == null)
            throw new ArgumentNullException();

        this.assetID = assetID;
        this.instanceID = instanceID;
    }

    public String getAssetID() { return assetID; }
    public String getInstanceID() { return instanceID; }
    public GridPosition getPosition() { return position; }

    public EnemyMapData setPosition(GridPosition position) throws ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
        this.position = position;
        return this;
    }
}
