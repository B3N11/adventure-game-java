package game.global;

import exception.general.ArgumentNullException;
import exception.save.CurrentSaveUnmodifiableException;
import file.handlers.FileHandler;

public class SaveHandler {
    
    private String currentSavePath;
    private boolean modifiable;     //If set to false, quick save cannot be used

    public void setCurrentSavePath(String path) throws ArgumentNullException{
        if(path == null)
            throw new ArgumentNullException();
        currentSavePath = path;
    }

    public void setModifiable(boolean modifiable){
        this.modifiable = modifiable;
    }

    public void quickSave() throws CurrentSaveUnmodifiableException{
        if(!modifiable)
            throw new CurrentSaveUnmodifiableException();

        try{ save(currentSavePath, false); }
        catch(ArgumentNullException e){}
    }

    public void save(String filePath, boolean appendFileExtension) throws ArgumentNullException{
        if(filePath == null)
            throw new ArgumentNullException();

        FileHandler.getInstance().saveProgress(filePath, appendFileExtension);
        setCurrentSavePath(filePath);
    }
}