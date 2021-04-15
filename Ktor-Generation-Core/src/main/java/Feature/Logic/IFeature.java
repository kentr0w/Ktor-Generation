package Feature.Logic;

import Feature.CoreFeatures.Project;
import Generation.Project.Node;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface IFeature {
    public void start();
    public String getId();
    
    public default Boolean replaceTextByHash(String pathToFile, String hash, String text) {
        try {
            String fileText = FileUtils.readFileToString(new File(pathToFile), "UTF-8");
            fileText = fileText.replace(hash, text);
            FileUtils.write(new File(pathToFile), fileText, "UTF-8");
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public default Boolean duplicateCodeFromTemplateToFile(String pathToCodeTmp, String resultFile) {
        try{
            byte[] code = Files.readAllBytes(Path.of(pathToCodeTmp));
            Files.write(Path.of(resultFile), code, StandardOpenOption.APPEND);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public default String getCodeAfterReplace(String pathToTemplate, List<String> hash, List<String> text) {
        try{
            String fileText = FileUtils.readFileToString(new File(pathToTemplate), "UTF-8");
            for (int i = 0; i < hash.size(); i++) {
                fileText = fileText.replace(hash.get(i), text.get(i));
            }
            return fileText;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public default String getSrcPath(String path) {
        return Arrays.asList(path.split(File.separator))
            .stream()
            .takeWhile(it -> !it.equals("src"))
            .collect(Collectors.joining(File.separator))
            + File.separator + "src";
    }
}
