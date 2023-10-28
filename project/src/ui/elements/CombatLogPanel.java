package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class CombatLogPanel extends JPanel{
    
    private JTextArea textArea;

    public CombatLogPanel(int width, int height){
        initPanel(width, height);
        setupTextArea(width, height);
    }

    private void initPanel(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);

        setLayout(new GridLayout(1,1));
        setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
    }

    private void setupTextArea(int width, int height){
        textArea = new JTextArea(1,1);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(width, height));

        var scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(textArea);
    }

    public void addToCombatLog(String text){
        textArea.append(text);
    }
}
