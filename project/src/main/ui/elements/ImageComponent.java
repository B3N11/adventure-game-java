package main.ui.elements;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.exception.general.ArgumentNullException;
import main.ui.data.GridDimension;

/**
 * This class represents an image component in the UI. It displays an image with the specified width and height.
 * It extends the JLabel class and is used to display an image.
 * 
 * The class contains the following field:
 * - preferredSize: The preferred size of the image component.
 */
public class ImageComponent extends JLabel{
    
    protected GridDimension preferredSize;

    /**
     * Constructor for the ImageComponent class.
     * Initializes the image component with the specified width and height.
     * @param width The width of the image component.
     * @param height The height of the image component.
     */
    public ImageComponent(int width, int height){
        preferredSize = new GridDimension(width, height);
        setPreferredSize(preferredSize);
        setBounds(0, 0, width, height);
    }

    /**
     * Constructor for the ImageComponent class.
     * Initializes the image component with the specified width, height, and file path.
     * @param width The width of the image component.
     * @param height The height of the image component.
     * @param filePath The file path of the image.
     * @throws ArgumentNullException if the file path is null.
     * @throws IOException if the file does not exist or is a directory.
     */
    public ImageComponent(int width, int height, String filePath) throws ArgumentNullException, IOException{
        this(width, height);
        setImage(filePath);
    }

    /**
     * Sets the image of the image component.
     * @param filePath The file path of the image.
     * @return The image component itself, for chaining.
     * @throws ArgumentNullException if the file path is null.
     * @throws IOException if the file does not exist or is a directory.
     */
    public ImageComponent setImage(String filePath) throws ArgumentNullException, IOException{
        if(filePath == null)
            throw new ArgumentNullException();

        var file = new File(filePath);
        if(!file.exists() || file.isDirectory())
            throw new IOException("File on path " + filePath + " either does not exist or is a directory!");        

        Image image = ImageIO.read(file).getScaledInstance(preferredSize.getHorizontal(), preferredSize.getVertical(), Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(image));
        
        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.getHorizontal(), preferredSize.getVertical());
        refresh();
        
        return this;
    }

    /**
     * Refreshes the image component.
     * Revalidates and repaints the image component.
     */
    public void refresh(){
        revalidate();
        repaint();
    }
}
