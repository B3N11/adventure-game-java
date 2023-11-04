package ui.elements;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LabelPanel extends JPanel{
    
    private JLabel text;

    public LabelPanel(int width, int height){
        initPanel(width, height);
        setupLabel(width, height);
    }

    public LabelPanel setLabelText(String text){
        this.text.setText(text);
        return this;
    }

    private void initPanel(int width, int height){
        setPreferredSize(new Dimension(width, height));
    }

    private void setupLabel(int width, int height){
        text = new JLabel();
        text.setPreferredSize(new Dimension(width, height));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        add(text);
    }
}
