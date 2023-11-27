package ui.elements;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import file.elements.MapLayoutData;
import uilogic.GridButtonHandler;

/**
 * This class represents the main play frame of the game. It is the main interface of the game.
 * It extends the JFrame class and contains various panels for different parts of the game interface.
 * 
 * The class contains the following fields:
 * - panel: The main panel of the frame.
 * - playfieldPanel: The panel for the playfield.
 * - combatLogPanel: The panel for the combat log.
 * - interactButtonPanel: The panel for the interaction buttons.
 * - utilityButtonPanel: The panel for the utility buttons.
 * - PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT: The dimensions of the playfield panel.
 * - UTILITYPANEL_WIDTH, UTILITYPANEL_HEIGHT: The dimensions of the utility panel.
 * - INTERACTPANEL_WIDTH, INTERACTPANEL_HEIGHT: The dimensions of the interaction panel.
 * - COMBATLOGPANEL_WIDTH, COMBATLOGPANEL_HEIGHT: The dimensions of the combat log panel.
 */
public class PlayFrame extends JFrame{

    private JPanel panel;
    private InteractiveGridPanel playfieldPanel;
    private ScrollableTextPanel combatLogPanel;
    private InteractButtonPanel interactButtonPanel;
    private UtilityButtonPanel utilityButtonPanel;

    //Sizes
    public static int PLAYFIELD_WIDTH = 1200;
    public static int PLAYFIELD_HEIGHT = 675;
    public static int UTILITYPANEL_WIDTH = 300;
    public static int UTILITYPANEL_HEIGHT = 844;
    public static int INTERACTPANEL_WIDTH = 1200;
    public static int INTERACTPANEL_HEIGHT = 169;
    public static int COMBATLOGPANEL_WIDTH = 300;
    public static int COMBATLOGPANEL_HEIGHT = 844;

    /**
     * Constructor for the PlayFrame class.
     * Initializes the play frame with the specified action listeners and window adapter.
     * @param menuBarListener The action listener for the menu bar.
     * @param utilityButtonListener The action listener for the utility buttons.
     * @param interactButtonListener The action listener for the interaction buttons.
     * @param closeOperation The window adapter for the close operation.
     * @throws Exception if the initialization of the play frame throws an Exception.
     */
    public PlayFrame(ActionListener menuBarListener, ActionListener utilityButtonListener, ActionListener interactButtonListener, WindowAdapter closeOperation) throws Exception{
        initPlayFrame(menuBarListener, utilityButtonListener, interactButtonListener, closeOperation);
    }

    public InteractiveGridPanel getPlayField() { return playfieldPanel; }

    /**
     * Initializes the play frame.
     * Initializes the frame, sets up the menu bar, and sets up the grid bag layout.
     * @param menuBarListener The action listener for the menu bar.
     * @param utilityButtonListener The action listener for the utility buttons.
     * @param interactButtonListener The action listener for the interaction buttons.
     * @param closeOperation The window adapter for the close operation.
     * @throws Exception if the initialization of the play frame throws an Exception.
     */
    private void initPlayFrame(ActionListener menuBarListener, ActionListener utilityButtonListener, ActionListener interactButtonListener, WindowAdapter closeOperation) throws Exception{
        initFrame(closeOperation);
        setupMenuBar(menuBarListener);
        setupGridBagLayout(utilityButtonListener, interactButtonListener);
        setVisible(true);
        pack();
    }

    /**
     * Initializes the frame.
     * Sets the title, size, location, default close operation, and layout of the frame, and adds the window listener to the frame.
     * @param closeOperation The window adapter for the close operation.
     */
    private void initFrame(WindowAdapter closeOperation){
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(closeOperation);

        panel = new JPanel(new GridBagLayout());
        setTitle("Adventure Game");
        setContentPane(panel);
    }

    /**
     * Sets up the menu bar.
     * Initializes the menu bar, sets its menu items, and adds it to the frame.
     * @param menuBarListener The action listener for the menu bar.
     */
    private void setupMenuBar(ActionListener menuBarListener) throws ArgumentNullException{
        if(menuBarListener == null)
            throw new ArgumentNullException();

        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        var fileSubMenu = new JMenu("File");
        var loadBaseInfoMenuItem = new JMenuItem("Load Config File");
        loadBaseInfoMenuItem.setActionCommand("LOAD_CONFIGFILE");
        loadBaseInfoMenuItem.addActionListener(menuBarListener);
        gameMenu.add(fileSubMenu);
        
        var saveSubMenu = new JMenu("Save Game");
        var loadMenuItem = new JMenuItem("Load Game");
        loadMenuItem.setActionCommand("LOAD_GAME");
        loadMenuItem.addActionListener(menuBarListener);
        fileSubMenu.add(saveSubMenu);
        fileSubMenu.add(loadMenuItem);
        fileSubMenu.add(loadBaseInfoMenuItem);
        
        var saveQuickMenuItem = new JMenuItem("Quick Save");
        saveQuickMenuItem.setActionCommand("QUICK_SAVE_GAME");
        saveQuickMenuItem.addActionListener(menuBarListener);
        var saveNewMenuItem = new JMenuItem("Save as New");
        saveNewMenuItem.setActionCommand("NEW_SAVE_GAME");
        saveNewMenuItem.addActionListener(menuBarListener);
        saveSubMenu.add(saveQuickMenuItem);
        saveSubMenu.add(saveNewMenuItem);
    }   
    
    /**
     * Sets up the grid bag layout. Fills it with the panels.
     * Initializes the grid bag constraints, sets the gridx and gridy values, and adds the components to the panel.
     */
    private void setupGridBagLayout(ActionListener utilityButtonListener, ActionListener interactButtonListener) throws Exception{
        var gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        utilityButtonPanel = new UtilityButtonPanel(UTILITYPANEL_WIDTH, UTILITYPANEL_HEIGHT, utilityButtonListener);
        addToPanel(utilityButtonPanel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        playfieldPanel = new InteractiveGridPanel(PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT);
        addToPanel(playfieldPanel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        interactButtonPanel= new InteractButtonPanel(INTERACTPANEL_WIDTH, INTERACTPANEL_HEIGHT, interactButtonListener);
        addToPanel(interactButtonPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        combatLogPanel = new ScrollableTextPanel(COMBATLOGPANEL_WIDTH, COMBATLOGPANEL_HEIGHT);
        addToPanel(combatLogPanel, gbc);
    }

    /**
     * Adds a component to the main panel.
     * @param comp The component to add.
     * @param gbc The grid bag constraints.
     */
    private void addToPanel(Component comp, GridBagConstraints gbc){
        panel.add(comp, gbc);
    }

    /**
     * Modifies the map layout of the playfield panel.
     * @param data The new map layout data.
     * @param buttonHandler The new grid button handler.
     * @param force A boolean that determines whether to force the modification.
     * @throws ArgumentNullException if the data or the button handler is null.
     * @throws Exception if the modification of the map layout throws an Exception.
     */
    public void modifyMapLayout(MapLayoutData data, GridButtonHandler buttonHandler, boolean force) throws Exception{
        if(data == null || buttonHandler == null)
            throw new ArgumentNullException();

        playfieldPanel.setMapLayout(data, buttonHandler, force);
    }

    /**
     * Adds a text to the combat log panel.
     * @param text The text to add.
     */
    public void addToCombatLog(String text){
        combatLogPanel.addToText(text);
    }

    /**
     * Clears the combat log panel.
     */
    public void clearCombatLog(){
        combatLogPanel.clearText();
    }

    /**
     * Refreshes the frame.
     * Revalidates and repaints the frame.
     */
    public void refresh(){
        revalidate();
        repaint();
    }

    /**
     * Toggles the player controls.
     * Enables or disables the utility buttons and interaction buttons, and refreshes the frame.
     * @param enabled A boolean that determines whether to enable or disable the player controls.
     */
    public void togglePlayerControlls(boolean enabled){
        utilityButtonPanel.toggleButtons(enabled);
        interactButtonPanel.toggleButtons(enabled);
        refresh();
    }
}