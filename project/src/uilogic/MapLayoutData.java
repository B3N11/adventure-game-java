package uilogic;

public class MapLayoutData {
    
    private int horizontal;
    private int vertical;
    private String filePath;

    public MapLayoutData(int x, int y, String file){        
        horizontal = x;
        vertical = y;
        filePath = file;
    }

    public int getHorizontal() { return horizontal; }
    public int getVertical() { return vertical; }
    public String getFilePath() { return filePath; }
}
