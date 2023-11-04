package file.elements;

import java.io.Serializable;

import exception.general.ArgumentNullException;
import uilogic.GridPosition;

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
    public GridPosition gerPosition() { return position; }

    public EnemyMapData setPosition(GridPosition position) throws ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
        this.position = position;
        return this;
    }
}
