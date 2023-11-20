package file.elements;

import exception.general.ArgumentNullException;
import game.utility.general.Identifiable;

public class IconData extends Identifiable{
    
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
