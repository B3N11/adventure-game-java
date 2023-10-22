package ui.elements;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;

public class PlayFrame extends JFrame{

    public static int WIDTH = 1500;
    public static int HEIGHT = 844;

    private JPanel panel;

    public PlayFrame(ActionListener menuBarListener) throws Exception{
        initPlayFrame(menuBarListener);
    }

    private void initPlayFrame(ActionListener menuBarListener) throws Exception{
        initFrame();
        setupMenuBar(menuBarListener);
        setupGridBagLayout();
        displayFrame();
        pack();
    }

    private void initFrame(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(0, 0, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        setContentPane(panel);
    }

    private void setupMenuBar(ActionListener menuBarListener){
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
    
    private void setupGridBagLayout() throws Exception{
        var gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.gridwidth = 1;
        //gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.weightx = 0.2;
        //gbc.weighty = 1;
        addToPanel(new WorldActionPanel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        //gbc.gridwidth = 1;
        //gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.weightx = 1;
        //gbc.weighty = 1;
        addToPanel(new PlayfieldPanel(new MapLayoutData(20, 11, "project/resources/img/maps/2.jpg"), new GridButtonHandler()), gbc);

        //addToPanel(gbcComponent(0, 0, 1, 3, gbc), gbc);
        //addToPanel(gbcComponent(1, 0, 5, 1, gbc), gbc);
        //addToPanel(gbcComponent(1, 1, 1, 1, gbc), gbc);
        //addToPanel(gbcComponent(2, 1, 1, 1, gbc), gbc);
    }

    private void addToPanel(Component comp, GridBagConstraints gbc){
        panel.add(comp, gbc);
    }

    private JPanel gbcComponent(int x, int y, int w, int h, GridBagConstraints gbc){

        gbc.gridx = x; 
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel panel1 = new JPanel();
        JTextField text = new JTextField("(" + w + ", " + h + ")");
        panel1.setBorder(new TitledBorder("(" + x + ", " + y + ")"));       
        panel1.setPreferredSize(new Dimension(w*100, h*100)); 
        panel1.setBounds(0, 0, w*100, h*100);
        panel1.add(text);

        return panel1;
    }

    public void refresh(){
        revalidate();
        repaint();
    }

    public void displayFrame(){
        setVisible(true);
    }
}