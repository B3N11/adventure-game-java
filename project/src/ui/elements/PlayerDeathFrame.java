package ui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerDeathFrame extends JFrame{
    
    public PlayerDeathFrame(ActionListener listener){
        initFrame();
        setupPanel(listener);
    }

    private void initFrame(){
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
        setResizable(false);
    }

    private void setupPanel(ActionListener listener){
        var panel = new JPanel(new GridLayout(1,3));

        var title = new JLabel("YOU DIED!");
        panel.add(title);

        var loadSaveButton = new CustomButton(300, 100);
        loadSaveButton.addActionListener(listener);
        loadSaveButton.setActionCommand("LOAD");
        panel.add(loadSaveButton);

        var quitGameButton = new CustomButton(300, 100);
        panel.add(quitGameButton);

        add(panel);
        revalidate();
        repaint();
    }
}
