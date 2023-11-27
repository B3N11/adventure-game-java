package file.elements;

import java.io.Serializable;

/**
 * This class represents the saved game configuration.
 * It includes the game settings, player settings, and other related data.
 * 
 * The class contains the following fields:
 * - itemFolder: The relative path to the folder containing item configuration files.
 * - enemyFolder: The relative path to the folder containing enemy configuration files.
 * - mapdataFolder: The relative path to the folder containing map data files.
 * - imageAssetFolder: The relative path to the folder containing image assets.
 * - defaultMapID: The ID of the default map to be loaded.
 * - defaultPlayerSaveFile: The relative path to the default player save file.
 */
public class GameConfigSave implements Serializable{
    //Folder paths to the game config files. They are relative to the path of the game config file
    public String itemFolder;
    public String enemyFolder;
    public String mapdataFolder;
    public String imageAssetFolder;

    public String defaultMapID;
    public String defaultPlayerSaveFile;
}