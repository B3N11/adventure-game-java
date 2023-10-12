package ui.elements;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import exception.general.ArgumentNullException;
import uilogic.MapLayoutData;
import ui.data.UIDataConfig;

//Manages UI related to the area where the game is displayed
public class PlayfieldPanel extends JLayeredPane{
    
    private JLabel background;
    private GridPanel entityPanel;
    private GridPanel buttonPanel;

    public PlayfieldPanel(MapLayoutData layoutData) throws ArgumentNullException {
        if(layoutData == null)
            throw new ArgumentNullException();

        initPlayfield(layoutData);
    }

    private void initPlayfield(MapLayoutData layoutData){
        setBounds(0, 0, UIDataConfig.MAPGRID_X, UIDataConfig.MAPGRID_Y);
        initBackground(UIDataConfig.MAPGRID_X, UIDataConfig.MAPGRID_Y, layoutData.getFilePath());
    }

    private void initBackground(int x, int y, String file){
        background = new JLabel(new ImageIcon(file));
        background.setBounds(0, 0, x, y);
    }

    private void initEntityPanel(MapLayoutData layoutData){
        
    }
}