package ui.elements;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CombatFrame extends JFrame{
    
    private CustomButton button;
    private ScrollableTextPanel textPanel;

    public static int IMAGE_WIDTH = 250;
    public static int IMAGE_HEIGHT = 400;

    public static int TEXTPANEL_WIDTH = 500;
    public static int TEXTPANEL_HEIGHT = 400;

    public static int BUTTONPANEL_WIDTH = 500;
    public static int BUTTONPANEL_HEIGHT = 100;

    public static int BUTTON_WIDTH = 300;
    public static int BUTTON_HEIGHT = 50;

    public static int LABEL_WIDTH = 250;
    public static int LABEL_HEIGHT = 100;

    public CombatFrame(String playerImagePath, String enemyImagePath, String enemyData) throws Exception{
        initFrame();
        setupFrameContent(playerImagePath, enemyImagePath, enemyData);
        pack();
    }

    private void initFrame(){
        setUndecorated(true);
        setResizable(false);
        setLayout(new GridBagLayout());
    }

    private void setupFrameContent(String playerImagePath, String enemyImagePath, String enemyData) throws Exception{
        var gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        var playerImage = new ImageComponent(IMAGE_WIDTH, IMAGE_HEIGHT).setImage(playerImagePath);
        add(playerImage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        var playerLabel = new LabelPanel(LABEL_WIDTH, LABEL_HEIGHT).setLabelText("Player");
        add(playerLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        var buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(BUTTONPANEL_WIDTH, BUTTONPANEL_HEIGHT));
        button = new CustomButton(BUTTON_WIDTH, BUTTON_HEIGHT, "Roll!");
        buttonPanel.add(button, JPanel.CENTER_ALIGNMENT);
        add(buttonPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        textPanel = new ScrollableTextPanel(TEXTPANEL_WIDTH, TEXTPANEL_HEIGHT);
        add(textPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        var enemyImage = new ImageComponent(IMAGE_WIDTH, IMAGE_HEIGHT).setImage(enemyImagePath);
        add(enemyImage, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        var enemyLabel = new LabelPanel(LABEL_WIDTH, LABEL_HEIGHT).setLabelText(enemyData);
        add(enemyLabel, gbc);
    }

    public CombatFrame setLocationOrigin(Component component){
        setLocationRelativeTo(component);
        return this;
    }

    public void addToText(String text){
        textPanel.addToText(text);
    }
}
