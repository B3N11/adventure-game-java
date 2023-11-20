package ui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PlayerDeathFrame extends JFrame{
    
    public PlayerDeathFrame(ActionListener listener){
        initFrame();
        setupPanel(listener);
    }

    private void initFrame(){
        setLayout(new GridLayout(1,3));
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
    }

    private void setupPanel(ActionListener listener){
        var title = new JLabel("YOU DIED!");
        add(title);

        var loadSaveButton = new CustomButton(300, 100);
        loadSaveButton.addActionListener(listener);
        loadSaveButton.setActionCommand("LOAD");
        add(loadSaveButton);

        var quitGameButton = new CustomButton(300, 100);
        add(quitGameButton);
    }
}
