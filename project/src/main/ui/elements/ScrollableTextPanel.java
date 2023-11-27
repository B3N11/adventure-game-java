package main.ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * This class represents a scrollable text panel in the UI. It is a panel with a scrollable text area.
 * It extends the JPanel class and contains a JTextArea for displaying text.
 * 
 * The class contains the following field:
 * - textArea: The JTextArea that is displayed in the panel.
 */
public class ScrollableTextPanel extends JPanel{
    
    private JTextArea textArea;

    /**
     * Constructor for the ScrollableTextPanel class.
     * Initializes the panel and sets up the text area with the specified width and height.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    public ScrollableTextPanel(int width, int height){
        initPanel(width, height);
        setupTextArea(width, height);
    }

    /**
     * Initializes the panel.
     * Sets the preferred size, bounds, layout, and border of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    private void initPanel(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);

        setLayout(new GridLayout(1,1));
        setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
    }

    /**
     * Sets up the text area in the panel.
     * Initializes the text area, sets its editable status, wrap style word status, line wrap status, and margin, and adds it to the panel.
     * @param width The width of the text area.
     * @param height The height of the text area.
     */
    private void setupTextArea(int width, int height){
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setMargin( new Insets(10,5,10,5) );

        var scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll);
    }
    
    /**
     * Adds a text to the text area.
     * Appends the text followed by a newline character to the text area, and sets the caret position to the end of the text area.
     * @param text The text to add.
     */
    public void addToText(String text){
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    /**
     * Clears the text area.
     * Sets the text of the text area to an empty string.
     */
    public void clearText(){
        textArea.setText("");
    }
}
