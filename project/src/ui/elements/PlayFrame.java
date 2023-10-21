package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;

public class PlayFrame extends JFrame{

    public static int PLAYFRAME_WIDTH = 1500;
    public static int PLAYFRAME_HEIGHT = 844;

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
        setPreferredSize(new Dimension(PLAYFRAME_WIDTH, PLAYFRAME_HEIGHT));
        setBounds(0, 0, PLAYFRAME_WIDTH, PLAYFRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
    }

    private void setupMenuBar(ActionListener menuBarListener){
        var menubar = new JMenuBar();
        setJMenuBar(menubar);

        var gameMenu = new JMenu("Game");
        var creatorMenu = new JMenu("Creator");
        menubar.add(gameMenu);
        menubar.add(creatorMenu);

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
    
    public void setupGridBagLayout() throws Exception{
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        var panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(300, 100));
        panel1.setBounds(0, 0, 300, 100);
        panel1.setBackground(Color.red);
        add(panel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new PlayfieldPanel(new MapLayoutData(20, 11, "project/resources/img/maps/2.jpg"), new GridButtonHandler()));

    }

    public void refresh(){
        revalidate();
        repaint();
    }

    public void displayFrame(){
        setVisible(true);
    }
}