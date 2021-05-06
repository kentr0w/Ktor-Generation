package Feature.Logic;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public default Boolean replaceListTextByHash(String pathToFile, List<String> hash, List<String> text) {
        Boolean answer = true;
        for (int i = 0; i < hash.size(); i++) {
            answer = replaceTextByHash(pathToFile, hash.get(i), text.get(i));
        }
        return answer;
    }
    
    public default Boolean duplicateCodeFromTemplateToFile(InputStream inputStream, String resultFile) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String fileText = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        try{
            Files.write(Path.of(resultFile), fileText.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public default String getCodeAfterReplace(InputStream inputStream, List<String> hash, List<String> text) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String fileText = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        for (int i = 0; i < hash.size(); i++) {
            fileText = fileText.replace(hash.get(i), text.get(i));
        }
        return fileText;
    }
    
    public default String getSrcPath(String path) {
        return Arrays.asList(path.split(File.separator))
            .stream()
            .takeWhile(it -> !it.equals("src"))
            .collect(Collectors.joining(File.separator))
            + File.separator + "src";
    }
}
