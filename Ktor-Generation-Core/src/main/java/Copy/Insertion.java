package Copy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Insertion {
    
    public static Boolean insertDependency(File fileFromInsert, File fileToInsert) {
        String importFileFrom = calculateImport(fileFromInsert, fileToInsert);
        if (importFileFrom != null) {
            if (insertCodeInNecessaryPlace(fileToInsert, "import ", importFileFrom)) {
                CustomLogger.writeLog(LogType.INFO, "Import was added" + importFileFrom);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public static Boolean insertCodeWithImportInFile(File fileFromInsert, File fileToInsert, String lineAfterToInsert, String codeToInsert) {
        if (insertDependency(fileFromInsert, fileToInsert)) {
            return insertCodeInNecessaryPlace(fileToInsert, lineAfterToInsert, codeToInsert);
        } else {
            return false;
        }
    }
    
    public static Boolean addImports(File fileWithCode, File importFile) {
        try{
            String imports = Files.readString(Path.of(importFile.getPath()));
            return insertCodeInNecessaryPlace(fileWithCode, "import ", imports);
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    private static Boolean insertCodeInNecessaryPlace(File file, String lineAfterToInsert, String codeToInsert) {
        try{
            List<String> textFromFile = FileUtils.readLines(file, "UTF-8");
            textFromFile.stream().filter(it -> it.startsWith(lineAfterToInsert)).findFirst().ifPresentOrElse(
                    it -> textFromFile.add(textFromFile.indexOf(it) + 1, codeToInsert),
                    () -> textFromFile.add(1, System.lineSeparator() + codeToInsert));
            String resultText = textFromFile.stream().collect(Collectors.joining(System.lineSeparator()));
            FileUtils.write(file, resultText, "UTF-8");
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    private static String calculateImport(File fileFrom, File fileTo) {
        if (fileFrom.getPath().equals(fileTo.getPath()))
            return null;
        try{
            String result = StringUtils.removeStart(FileUtils.readLines(fileFrom, "UTF-8").get(0), "package ");
            return "import " + result + ".*";
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
