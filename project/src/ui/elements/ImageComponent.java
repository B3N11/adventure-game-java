package ui.elements;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import exception.general.ArgumentNullException;
import ui.data.GridDimension;

public class ImageComponent extends JLabel{
    
    protected GridDimension preferredSize;

    public ImageComponent(int width, int height){
        preferredSize = new GridDimension(width, height);
        setPreferredSize(preferredSize);
        setBounds(0, 0, width, height);
    }

    public ImageComponent(int width, int height, String filePath) throws ArgumentNullException, IOException{
        this(width, height);
        setImage(filePath);
    }

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

    public void refresh(){
        revalidate();
        repaint();
    }
}
