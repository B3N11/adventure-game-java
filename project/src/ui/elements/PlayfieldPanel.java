package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import exception.general.ArgumentNullException;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;
import ui.data.GridDimension;

//Manages UI related to the area where the game is displayed
public class PlayfieldPanel extends JLayeredPane{
    
    private JLabel background;
    private GridPanel entityPanel;
    private GridPanel buttonPanel;

    private static GridDimension preferedSize = new GridDimension(1000, 563);

    public PlayfieldPanel(MapLayoutData layoutData, GridButtonHandler handler) throws Exception {
        if(layoutData == null || handler == null)
            throw new ArgumentNullException();

        initPlayfield(layoutData, handler);
    }

    private void initPlayfield(MapLayoutData layoutData, GridButtonHandler handler) throws Exception{
        setPreferredSize(preferedSize);
        setBounds(0, 0, (int)preferedSize.getWidth(), (int)preferedSize.getHeight());

        initBackground(preferedSize.getHorizontal(), preferedSize.getVertical(), layoutData.getFilePath());
        initEntityPanel(layoutData.getHorizontal(), layoutData.getVertical());
        initButtonPanel(layoutData.getHorizontal(), layoutData.getVertical(), handler);

        add(background, Integer.valueOf(0));
        add(entityPanel.getJPanel(), Integer.valueOf(1));
        add(buttonPanel.getJPanel(), Integer.valueOf(2));

        revalidate();
        repaint();
    }

    private void initBackground(int x, int y, String file) throws Exception{
        Image asd = ImageIO.read(new File(file));
        Image image = asd.getScaledInstance(x, y, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        background = new JLabel(icon);
        background.setPreferredSize(new Dimension(x, y));
        background.setBounds(0, 0, x, y);
    }

    private void initEntityPanel(int x, int y) throws Exception{
        entityPanel = new GridPanel(preferedSize.getHorizontal(), preferedSize.getVertical());
        var gbc = new GridBagConstraints();

        //Dinamically set the size of components based on the resolution of GridPanel and the number of components
        int xComponentSize = preferedSize.getHorizontal() / x;
        int yComponentSize = preferedSize.getVertical() / y;

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                entityPanel.add(new DummyComponent(xComponentSize, yComponentSize, gbc), gbc, false);
            }
        }
    }

    private void initButtonPanel(int x, int y, GridButtonHandler handler) throws Exception{
        buttonPanel = new GridPanel(preferedSize.getHorizontal(), preferedSize.getVertical());
        var gbc = new GridBagConstraints();
        var color = new Color(255, 0, 0, 50);

        //Dinamically set the size of buttons based on the resolution of GridPanel and the number of buttons
        int xButtonSize = preferedSize.getHorizontal() / x;
        int yButtonSize = preferedSize.getVertical() / y;

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                buttonPanel.add(new MapGridButton(xButtonSize, yButtonSize, color, gbc, handler), gbc, false);
            }
        }
    }
}