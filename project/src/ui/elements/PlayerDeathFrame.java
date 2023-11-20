package ui.elements;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;

public class PlayerDeathFrame extends JFrame implements ActionListener{
    
    public PlayerDeathFrame(Component relativeLocation, ActionListener listener) throws ArgumentNullException{
        if(listener == null)
            throw new ArgumentNullException();

        initFrame(relativeLocation);
        try{ setupPanel(listener); }
        catch(ArgumentNullException e){}
        pack();
    }

    private void initFrame(Component relativeLocation){
        setTitle("Player Died");
        setLocationRelativeTo(relativeLocation);
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
        loadSaveButton.addActionListener(this);
        loadSaveButton.setActionCommand("LOAD_SAVE");
        panel.add(loadSaveButton);

        var quitGameButton = new CustomButton(300, 100, "Quit Game");
        quitGameButton.addActionListener(listener);
        quitGameButton.setActionCommand("QUIT_GAME");
        panel.add(quitGameButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        dispose();
    }
}