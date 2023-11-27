package main.ui.elements;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class represents a label panel in the UI. Its a panel with a centered label.
 * It extends the JPanel class and is used to display a label with optional border.
 * 
 * The class contains the following field:
 * - text: The JLabel that is displayed in the panel.
 * 
 * The class provides methods to set the text and font of the label, and to format the text.
 */
public class LabelPanel extends JPanel{
    
    private JLabel text;

    public LabelPanel(boolean border){
        initPanel(border);
        setupLabel();
    }

    /**
     * Sets the text of the label in the panel.
     * @param text The new text of the label.
     * @return The label panel itself, for chaining.
     */
    public LabelPanel setLabelText(String text){
        this.text.setText(formatText(text));
        return this;
    }

    /**
     * Sets the text and font of the label in the panel.
     * @param text The new text of the label.
     * @param font The new font of the label.
     * @return The label panel itself, for chaining.
     */
    public LabelPanel setLabelText(String text, Font font){
        this.text.setFont(font);
        this.text.setText(formatText(text));
        return this;
    }

    /**
     * Initializes the panel.
     * If border is true, it sets the border of the panel.
     * @param border A boolean that determines whether to set the border of the panel.
     */
    private void initPanel(boolean border){
        if(border)
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Sets up the label in the panel.
     * Initializes the label, sets its horizontal and vertical alignment, and adds it to the panel.
     */
    private void setupLabel(){
        text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        add(text);
    }

    /**
     * Formats the input text.
     * Wraps the input text in HTML tags and replaces newline characters with HTML line break tags.
     * @param input The input text.
     * @return The formatted text.
     */
    private String formatText(String input){
        String result = "<html>" + input + "</html>";
        return result.replace("\n", "<br/>");
    }
}
