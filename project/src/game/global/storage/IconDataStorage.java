package game.global.storage;

import file.elements.IconData;

public class IconDataStorage extends Storage<IconData>{
    
    private static IconDataStorage instance;

    private IconDataStorage(){}

    public static IconDataStorage getInstance(){
        if(instance == null)
            instance = new IconDataStorage();
        return instance;
    }
}
