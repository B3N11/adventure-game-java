package ui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;

public class PlayerDeathFrame extends JFrame{
    
    public PlayerDeathFrame(ActionListener listener) throws ArgumentNullException{
        if(listener == null)
            throw new ArgumentNullException();

        initFrame();
        try{ setupPanel(listener); }
        catch(ArgumentNullException e){}
    }

    private void initFrame(){
        setTitle("Player Died");
        //setUndecorated(true);
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
        setResizable(false);
    }

    private void setupPanel(ActionListener listener) throws ArgumentNullException{
        var panel = new JPanel(new GridLayout(3, 1));

        var title = new JLabel("YOU DIED!");
        panel.add(title);

        var loadSaveButton = new CustomButton(300, 100, "Load Save");
        loadSaveButton.addActionListener(listener);
        loadSaveButton.setActionCommand("LOAD_SAVE");
        panel.add(loadSaveButton);

        var quitGameButton = new CustomButton(300, 100, "Quit Game");
        quitGameButton.addActionListener(listener);
        quitGameButton.setActionCommand("QUIT_GAME");
        panel.add(quitGameButton);

        add(panel);
        revalidate();
        repaint();
        pack();
    }
}