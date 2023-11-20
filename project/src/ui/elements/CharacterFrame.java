package ui.elements;

import javax.swing.JFrame;
import java.awt.GridLayout;

public class CharacterFrame extends JFrame{
    
    public CharacterFrame(){
        initFrame();
        setUpContent();
        pack();
    }

    private void initFrame(){
        setTitle("Character");
        setAlwaysOnTop(true);
        setResizable(false);
        setLayout(new GridLayout(1,3));
    }

    private void setUpContent(){

    }
}
