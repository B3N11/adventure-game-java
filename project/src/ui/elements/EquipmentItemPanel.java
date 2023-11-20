package ui.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import ui.interfaces.IDisplayable;

public class EquipmentItemPanel extends JPanel{

    public static int PANEL_WIDTH = 280;
    public static int PANEL_HEIGHT = 280;
    public static int IMG_WIDTH = 100;
    public static int IMG_HEIGHT = 100;
    
    public EquipmentItemPanel(String title) throws ArgumentNullException{
        if(title == null)
            throw new ArgumentNullException();
            
        initPanel(title);
    }

    private void initPanel(String title){
        setBorder(BorderFactory.createTitledBorder(title));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(new GridBagLayout());
    }

    public EquipmentItemPanel setUpContent(IDisplayable item, String iconPath, int bearerLevel) throws ArgumentNullException, IOException{
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridheight = 1;

        gbc.gridy = 0;
        var icon = new ImageComponent(IMG_WIDTH, IMG_HEIGHT).setImage(iconPath);
        add(icon, gbc);
        
        gbc.gridy = 1;
        var nameLabel = new LabelPanel(false).setLabelText(item.getName(), new Font("Arial", Font.BOLD, 20));
        add(nameLabel, gbc);

        gbc.gridy = 2;
        var descLabel = new LabelPanel(false).setLabelText(item.getStatistics(bearerLevel));
        add(descLabel, gbc);

        repaint();
        revalidate();

        return this;
    }
}