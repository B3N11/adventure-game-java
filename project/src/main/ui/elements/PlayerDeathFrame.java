package main.ui.elements;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.exception.general.ArgumentNullException;

/**
 * This class represents a frame that is displayed when the player dies. It allows the player to load a save or quit the game.
 * It extends the JFrame class.
 * 
 * The class contains the following methods:
 * - PlayerDeathFrame: The constructor of the class.
 * - initFrame: Initializes the frame.
 * - setupPanel: Sets up the panel in the frame.
 */
public class PlayerDeathFrame extends JFrame{
    
    /**
     * Constructor for the PlayerDeathFrame class.
     * Initializes the frame with the specified relative location and action listener.
     * @param relativeLocation The component in relation to which the frame's location is determined.
     * @param listener The action listener of the frame.
     * @throws ArgumentNullException if the listener is null.
     */
    public PlayerDeathFrame(Component relativeLocation, ActionListener listener) throws ArgumentNullException{
        if(listener == null)
            throw new ArgumentNullException();

        initFrame(relativeLocation);
        try{ setupPanel(listener); }
        catch(ArgumentNullException e){}
        pack();
    }

    /**
     * Initializes the frame.
     * Sets the title, location, always-on-top status, auto-request-focus status, and resizable status of the frame.
     * @param relativeLocation The component in relation to which the frame's location is determined.
     */
    private void initFrame(Component relativeLocation){
        setTitle("Player Died");
        setLocationRelativeTo(relativeLocation);
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
        setResizable(false);
    }

    /**
     * Sets up the panel in the frame.
     * Initializes the panel with a grid layout, sets its components, and adds it to the frame.
     * @param listener The action listener of the panel.
     * @throws ArgumentNullException if the listener is null.
     */
    private void setupPanel(ActionListener listener) throws ArgumentNullException{
        var panel = new JPanel(new GridLayout(3, 1));

        var title = new JLabel("YOU DIED!");
        panel.add(title);

        var loadSaveButton = new CustomButton(300, 100, "Load Save");
        loadSaveButton.addActionListener(listener);
        loadSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        loadSaveButton.setActionCommand("LOAD_SAVE");
        panel.add(loadSaveButton);

        var quitGameButton = new CustomButton(300, 100, "Quit Game");
        quitGameButton.addActionListener(listener);
        quitGameButton.setActionCommand("QUIT_GAME");
        panel.add(quitGameButton);

        add(panel);
    }
}