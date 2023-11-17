package file.elements;

import java.io.Serializable;

public class GameConfigSave implements Serializable{
    //Folder paths to the game config files. They are relative to the path of the game config file
    public String itemFolder;
    public String enemyFolder;
    public String mapdataFolder;

    public String defaultMapID;
    public String defaultPlayerSaveFile;
}