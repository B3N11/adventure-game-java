package main.file.elements;

import main.exception.general.ArgumentNullException;
import main.game.utility.Identifiable;

/**
 * This class represents the icon data of a game element.
 * It includes the icon's normal path and absolute path.
 * 
 * The class contains the following fields:
 * - normalPath: The relative path to the icon.
 * - absolutPath: The absolute path to the icon.
 * 
 * The class provides getter methods for these fields.
 */
public class IconData extends Identifiable {
    
    private String normalPath;
    private String absoulutPath;

    public IconData(String normalPath, String absolutPath) throws ArgumentNullException{
        if(normalPath == null || absolutPath == null)
            throw new ArgumentNullException();
        this.normalPath = normalPath;
        this.absoulutPath = absolutPath;
    }

    public String getNormalPath() { return normalPath; }
    public String getAbsolutPath() { return absoulutPath; }
}
