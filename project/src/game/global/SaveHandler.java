package game.global;

import exception.general.ArgumentNullException;
import exception.save.CurrentSaveUnmodifiableException;

/**
 * This class handles the saving of the game.
 * It contains methods to set the current save path, set whether the save is modifiable, quick save, and save.
 * 
 * The class contains the following fields:
 * - currentSavePath: The path of the current save.
 * - modifiable: Whether the current save can be overwritten.
 */
public class SaveHandler {
    
    private String currentSavePath;
    private boolean modifiable;     //If set to false, quick save cannot be used

    public void setCurrentSavePath(String path) throws ArgumentNullException{
        if(path == null)
            throw new ArgumentNullException();
        currentSavePath = path;
    }

    /**
     * Sets whether the current save is modifiable.
     * @param modifiable Whether the current save is modifiable.
     */
    public void setModifiable(boolean modifiable){
        this.modifiable = modifiable;
    }

    /**
     * Quick saves the game.
     * If the current save is not modifiable, it throws a CurrentSaveUnmodifiableException.
     * @throws CurrentSaveUnmodifiableException if the current save is not modifiable.
     */
    public void quickSave() throws CurrentSaveUnmodifiableException{
        if(!modifiable)
            throw new CurrentSaveUnmodifiableException();

        try{ save(currentSavePath, false); }
        catch(ArgumentNullException e){}
    }

    /**
     * Saves the game.
     * @param filePath The path of the file to save to.
     * @param appendFileExtension Whether to append the file extension to the file path.
     * @throws ArgumentNullException if the file path is null.
     */
    public void save(String filePath, boolean appendFileExtension) throws ArgumentNullException{
        if(filePath == null)
            throw new ArgumentNullException();

        FileHandler.getInstance().saveProgress(filePath, appendFileExtension);
        setCurrentSavePath(filePath);
    }
}