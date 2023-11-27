package uilogic;

import java.awt.GridBagConstraints;
import java.io.Serializable;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;

public class GridPosition implements Serializable{
    
    private int x;
    private int y;

    public GridPosition() throws InvalidArgumentException{
        this(0,0);
    }

    public GridPosition(int x, int y) throws InvalidArgumentException{
        setPosition(x, y);
    }

    public GridPosition(GridBagConstraints gbc) throws InvalidArgumentException{
        this(gbc.gridx, gbc.gridy);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public GridBagConstraints getAsGridBagConstraints(){
        var result = new GridBagConstraints();
        result.gridx = x;
        result.gridy = y;
        return result;
    }

    public GridPosition setPosition(int x, int y) throws InvalidArgumentException{
        if(x < 0 || y < 0)
            throw new InvalidArgumentException();
        
        this.x = x;
        this.y = y;

        return this;
    }

    public static double calculateAbsoluteDistance(GridPosition src, GridPosition dst) throws ArgumentNullException{
        if(src == null || dst == null)
            throw new ArgumentNullException();

        double xDistance = Math.abs(src.getX() - dst.getX());
        double yDistance = Math.abs(src.getY() - dst.getY());
        double sum = Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
        double result = Math.sqrt(sum);        

        result = Double.parseDouble(String.format("%.2f", result));

        return result;
    }

    public boolean equals(GridPosition cmp){
        return (x == cmp.x) && (y == cmp.y);
    }
}
