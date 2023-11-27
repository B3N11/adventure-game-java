package uilogic;

import file.handlers.FileHandler;
import game.global.GameHandler;
import game.global.storage.MapStorage;
import game.utility.GenericDelegate;
import ui.elements.TravelFrame;

public class TravelFrameHandler {

    public void start(){
        var list = MapStorage.getInstance().getAllKeys();
        var frame = new TravelFrame(list.toArray(new String[list.size()]), new GenericDelegate() {
            public void run(Object o) { selectMap(o.toString()); }
        });
        frame.setVisible(true);
    }
    
    public void selectMap(String mapName){
        try{
            var id = MapStorage.getInstance().get(mapName);
            FileHandler.getInstance().loadCurrentMap(id, GameHandler.getInstance().getPlayer().getEntity().getInventory().getAllItems());
        }catch(Exception e){}
    }
}