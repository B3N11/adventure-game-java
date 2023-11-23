package ui.elements;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LabelPanel extends JPanel{
    
    private JLabel text;

    public LabelPanel(boolean border){
        initPanel(border);
        setupLabel();
    }

    public LabelPanel setLabelText(String text){
        this.text.setText(formatText(text));
        return this;
    }

    public LabelPanel setLabelText(String text, Font font){
        this.text.setFont(font);
        this.text.setText(formatText(text));
        return this;
    }

    private void initPanel(boolean border){
        if(border)
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setupLabel(){
        text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        add(text);
    }

    private String formatText(String input){
        String result = "<html>" + input + "</html>";
        return result.replace("\n", "<br/>");
    }
}
