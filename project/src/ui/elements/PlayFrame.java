package ui.elements;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;

public class PlayFrame extends JFrame{

    private JPanel panel;
    private PlayfieldPanel playfieldPanel;
    private ScrollableTextPanel combatLogPanel;

    //Sizes
    public static int PLAYFIELD_WIDTH = 1200;
    public static int PLAYFIELD_HEIGHT = 675;
    public static int UTILITYPANEL_WIDTH = 300;
    public static int UTILITYPANEL_HEIGHT = 844;
    public static int INTERACTPANEL_WIDTH = 1200;
    public static int INTERACTPANEL_HEIGHT = 169;
    public static int COMBATLOGPANEL_WIDTH = 300;
    public static int COMBATLOGPANEL_HEIGHT = 844;

    public PlayFrame(ActionListener menuBarListener, ActionListener utilityButtonListener, ActionListener interactButtonListener) throws Exception{
        initPlayFrame(menuBarListener, utilityButtonListener, interactButtonListener);
    }

    private void initPlayFrame(ActionListener menuBarListener, ActionListener utilityButtonListener, ActionListener interactButtonListener) throws Exception{
        initFrame();
        setupMenuBar(menuBarListener);
        setupGridBagLayout(utilityButtonListener, interactButtonListener);
        displayFrame();
        pack();
    }

    private void initFrame(){
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        setContentPane(panel);
    }

    private void setupMenuBar(ActionListener menuBarListener) throws ArgumentNullException{
        if(menuBarListener == null)
            throw new ArgumentNullException();

        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var gameMenu = new JMenu("Game");
        var creatorMenu = new JMenu("Creator");
        menuBar.add(gameMenu);
        menuBar.add(creatorMenu);

        var saveSubMenu = new JMenu("Save Game");
        var loadMenuItem = new JMenuItem("Load Game");
        loadMenuItem.setActionCommand("LOAD_GAME");
        loadMenuItem.addActionListener(menuBarListener);
        gameMenu.add(saveSubMenu);
        gameMenu.add(loadMenuItem);

        var saveQuickMenuItem = new JMenuItem("Quick Save");
        saveQuickMenuItem.setActionCommand("QUICK_SAVE_GAME");
        saveQuickMenuItem.addActionListener(menuBarListener);
        var saveNewMenuItem = new JMenuItem("Save as New");
        saveNewMenuItem.setActionCommand("NEW_SAVE_GAME");
        saveNewMenuItem.addActionListener(menuBarListener);
        saveSubMenu.add(saveQuickMenuItem);
        saveSubMenu.add(saveNewMenuItem);
    }   
    
    private void setupGridBagLayout(ActionListener utilityButtonListener, ActionListener interactButtonListener) throws Exception{
        var gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        var utilityButtonPanel = new UtilityButtonPanel(UTILITYPANEL_WIDTH, UTILITYPANEL_HEIGHT, utilityButtonListener);
        addToPanel(utilityButtonPanel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        playfieldPanel = new PlayfieldPanel(PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT);
        addToPanel(playfieldPanel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        var panel3 = new InteractButtonPanel(INTERACTPANEL_WIDTH, INTERACTPANEL_HEIGHT, interactButtonListener);
        addToPanel(panel3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        combatLogPanel = new ScrollableTextPanel(COMBATLOGPANEL_WIDTH, COMBATLOGPANEL_HEIGHT);
        addToPanel(combatLogPanel, gbc);
    }

    private void addToPanel(Component comp, GridBagConstraints gbc){
        panel.add(comp, gbc);
    }

    public void modifyMapLayout(MapLayoutData data, GridButtonHandler buttonHandler, boolean force) throws Exception{
        if(data == null || buttonHandler == null)
            throw new ArgumentNullException();

        playfieldPanel.setMapLayout(data, buttonHandler, force);
    }

    public void addToCombatLog(String text){
        combatLogPanel.addToText(text);
    }

    public void refresh(){
        revalidate();
        repaint();
    }

    public void displayFrame(){
        setVisible(true);
    }
}