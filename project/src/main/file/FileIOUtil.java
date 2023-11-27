package main.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import main.exception.general.ArgumentNullException;

public class FileIOUtil {

    public Object readObjectFromFile(String filePath) throws ArgumentNullException, FileNotFoundException, IOException, ClassNotFoundException{
        if(filePath == null)
            throw new ArgumentNullException();

        var stream = new ObjectInputStream(new FileInputStream(filePath));
        var result = stream.readObject();
        stream.close();

        return result;
    }

    /**
     * Reads an object from a file.
     *
     * @param file The file to read the object from.
     * @return The object read from the file.
     * @throws ArgumentNullException If the file is null.
     * @throws FileNotFoundException If the file does not exist.
     * @throws IOException If an I/O error occurs while reading.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public Object readObjectFromFile(File file) throws ArgumentNullException, FileNotFoundException, IOException, ClassNotFoundException{
        if(file == null)
            throw new ArgumentNullException();

        return readObjectFromFile(file.getPath());
    }

    /**
     * Writes a Serializable object to a file.
     *
     * @param filePath The path of the file to write the object to.
     * @param object The object to write to the file.
     * @throws ArgumentNullException If the filePath or the object is null.
     * @throws FileNotFoundException If the file does not exist.
     * @throws IOException If an I/O error occurs while writing.
     */
    public void writeObjectToFile(String filePath, Serializable object) throws ArgumentNullException, FileNotFoundException, IOException{
        if(filePath == null || object == null)
            throw new ArgumentNullException();

        var stream = new ObjectOutputStream(new FileOutputStream(filePath));
        stream.writeObject(object);
        stream.close();
    }

    /**
     * Reads a file and returns its content as a String.
     *
     * @param filePath The path of the file to read.
     * @return The content of the file as a String.
     * @throws ArgumentNullException If the filePath is null.
     * @throws FileNotFoundException If the file does not exist.
     */
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