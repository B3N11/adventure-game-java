package ui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import exception.general.ArgumentNullException;
import game.utility.delegates.GenericDelegate;

public class TravelFrame extends JFrame{

    private JComboBox<String> comboBox;
    private GenericDelegate onMapSelect;

    public TravelFrame(String[] mapNames, GenericDelegate onMapSelect){
        initFrame();
        setUpContent(mapNames);
        this.onMapSelect = onMapSelect;
    }
    
    private void initFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(2,1));
    }

    private void setUpContent(String[] mapNames){
        comboBox = new JComboBox<>(mapNames);   
        add(comboBox);

        try{
            var button = new CustomButton(200, 50, "Travel");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { selectMap(); }
            });
            add(button);
        }
        catch(ArgumentNullException e){}
        
        pack();
    }

    public void selectMap(){
        String item = comboBox.getSelectedItem().toString();
        if(onMapSelect != null)
            onMapSelect.run(item);
    }
}