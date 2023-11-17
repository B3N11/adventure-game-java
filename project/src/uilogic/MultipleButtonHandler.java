package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import game.utility.delegates.GenericDelegate;

public abstract class MultipleButtonHandler implements ActionListener{
    
    protected HashMap<String, GenericDelegate> actions;

    public MultipleButtonHandler(){
        actions = new HashMap<String, GenericDelegate>();
        initActions();
    }

    protected abstract void initActions();

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = actions.get(e.getActionCommand());

        if(action == null)
            return;
        action.run(null);
    }
}
