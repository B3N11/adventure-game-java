package game.global.storage;

public class MapStorage extends Storage<String>{
    
    private static MapStorage instance;

    private MapStorage(){}

    public static MapStorage getInstance(){
        if(instance == null)
            instance = new MapStorage();
        return instance;
    }
}
