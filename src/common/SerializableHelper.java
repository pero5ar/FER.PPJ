package common;

import java.io.*;

/**
 * Created by pero5ar on 7.11.2016..
 */
public class SerializableHelper {

    public static void createOutput(String outPath, Serializable... objects) {
        try {
            File file = new File(outPath);
            file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Serializable object : objects) {
                out.writeObject(object);
            }
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
