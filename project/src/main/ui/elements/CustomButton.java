package main.ui.elements;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import main.exception.general.ArgumentNullException;

/**
 * This class represents a custom button in the game.
 * It extends the JButton class and contains methods to initialize the button and set its text.
 * 
 * The class contains the following fields:
 * - width: The width of the button.
 * - height: The height of the button.
 * - text: The text of the button.
 */
public class CustomButton extends JButton{
    
    /**
     * Constructor for the CustomButton class.
     * Initializes the button with the specified width and height.
     * @param width The width of the button.
     * @param height The height of the button.
     */
    public CustomButton(int width, int height){
        initButton(width, height);
    }

    /**
     * Constructor for the CustomButton class.
     * Initializes the button with the specified width, height, and text.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param text The text of the button.
     * @throws ArgumentNullException if the text is null.
     */
    public CustomButton(int width, int height, String text) throws ArgumentNullException{
        this(width, height);
        setButtonText(text);
    }

    /**
     * Initializes the button.
     * Sets the preferred size, bounds, background color, foreground color, and border of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     */
    private void initButton(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);

        setBackground(Color.GRAY);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Sets the text of the button.
     * @param text The new text to set.
     * @return The button itself, for chaining.
     * @throws ArgumentNullException if the new text is null.
     */
    public CustomButton setButtonText(String text) throws ArgumentNullException{
        if(text == null)
            throw new ArgumentNullException();

        super.setText(text);
        return this;
    }
}
