package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties prop;
    //this method will read any  properties file
    public static Properties readProperties(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
          prop = new Properties();
            prop.load(fis);
            fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop;
    }

    //this method retrieves single values based on the specified key

    /**
     * returns value of a particular key
     * @param key
     * @return value of the key
     */
    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }

}