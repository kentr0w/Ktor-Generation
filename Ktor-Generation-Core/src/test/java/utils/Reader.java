package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    
    public static List<String> readFilesNameInFile(String path) {
        List<String> names = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                line = StringUtils.stripStart(line, " ");
                names.add(line.split(" ")[0]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return names;
    }
    
    public static List<String> readNamesInGeneratedFile() {
        List<String> res = new ArrayList<>();
        try {
            Files.walk(Path.of("user")).forEach(file -> {
                res.add(file.getFileName().toString());
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return res;
    }
}
