package game.global.storage;

public class IconFilePathStorage extends Storage<String>{
    
    private static IconFilePathStorage instance;

    private IconFilePathStorage(){}

    public static IconFilePathStorage getInstance(){
        if(instance == null)
            instance = new IconFilePathStorage();
        return instance;
    }
}
