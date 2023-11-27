package uilogic;

import game.global.FileHandler;
import game.global.GameHandler;
import game.global.storage.MapStorage;
import game.utility.GenericDelegate;
import ui.elements.TravelFrame;

/**
 * This class handles the travel frame in the UI.
 * It contains methods to start the travel frame and select a map.
 * 
 * The class contains the following methods:
 * - start: Starts the travel frame.
 * - selectMap: Selects a map.
 */
public class TravelFrameHandler {
    
    /**
     * Starts the travel frame.
     * Gets all the keys from the MapStorage, creates a new TravelFrame with the keys and a delegate to select a map, and sets the TravelFrame to visible.
     */
    public void start(){
        var list = MapStorage.getInstance().getAllKeys();
        var frame = new TravelFrame(list.toArray(new String[list.size()]), new GenericDelegate() {
            public void run(Object o) { selectMap(o.toString()); }
        });
        frame.setVisible(true);
    }
        
    /**
     * Selects a map.
     * Gets the ID of the map from the MapStorage, loads the current map with the ID and the items in the player's inventory, and catches any exceptions.
     * @param mapName The name of the map.
     */
    public void selectMap(String mapName){
        try{
            var id = MapStorage.getInstance().get(mapName);
            FileHandler.getInstance().loadCurrentMap(id, GameHandler.getInstance().getPlayer().getEntity().getInventory().getAllItems());
        }catch(Exception e){}
    }
}