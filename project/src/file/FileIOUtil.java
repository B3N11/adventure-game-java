package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import exception.general.ArgumentNullException;

public class FileIOUtil {

    public Object readObjectFromFile(String filePath) throws ArgumentNullException, FileNotFoundException, IOException, ClassNotFoundException{
        if(filePath == null)
            throw new ArgumentNullException();

        var stream = new ObjectInputStream(new FileInputStream(filePath));
        var result = stream.readObject();
        stream.close();

        return result;
    }

    public Object readObjectFromFile(File file) throws ArgumentNullException, FileNotFoundException, IOException, ClassNotFoundException{
        if(file == null)
            throw new ArgumentNullException();

        return readObjectFromFile(file.getPath());
    }

    public void writeObjectToFile(String filePath, Serializable object) throws ArgumentNullException, FileNotFoundException, IOException{
        if(filePath == null || object == null)
            throw new ArgumentNullException();

        var stream = new ObjectOutputStream(new FileOutputStream(filePath));
        stream.writeObject(object);
        stream.close();
    }

    public String readFile(String filePath) throws ArgumentNullException, FileNotFoundException{
        if(filePath == null)
            throw new ArgumentNullException();

        String result = "";
        var file = new File(filePath);
        var scanner = new Scanner(file);

        while(scanner.hasNext()){
            result += scanner.nextLine();
        }

        scanner.close();
        return result;
    }
}