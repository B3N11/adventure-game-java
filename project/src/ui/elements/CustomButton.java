package ui.elements;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import exception.general.ArgumentNullException;

public class CustomButton extends JButton{
    
    public CustomButton(int width, int height){
        initButton(width, height);
    }

    public CustomButton(int width, int height, String text) throws ArgumentNullException{
        this(width, height);
        setButtonText(text);
    }

    private void initButton(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);

        setBackground(Color.GRAY);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public CustomButton setButtonText(String text) throws ArgumentNullException{
        if(text == null)
            throw new ArgumentNullException();

        super.setText(text);
        return this;
    }
}
