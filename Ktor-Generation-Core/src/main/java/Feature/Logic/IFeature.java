package Feature.Logic;

import Generation.Project.Node;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface IFeature {
    public void start();
    public String getName();
    public default void replaceTextByHash(String pathToFile, String hash, String text) {
        try {
            String fileText = FileUtils.readFileToString(new File(pathToFile), "UTF-8");
            fileText = fileText.replace(hash, text);
            FileUtils.write(new File(pathToFile), fileText, "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
