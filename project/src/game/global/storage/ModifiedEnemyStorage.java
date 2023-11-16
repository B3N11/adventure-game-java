package game.global.storage;

import game.utility.dataclass.ModifiedEnemyData;

public class ModifiedEnemyStorage extends Storage<ModifiedEnemyData>{
    
    private static ModifiedEnemyStorage instance;

    private ModifiedEnemyStorage(){}

    public static ModifiedEnemyStorage getInstance(){
        if(instance == null)
            instance = new ModifiedEnemyStorage();
        return instance;
    }
}
