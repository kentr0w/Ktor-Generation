package Copy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Insertion {
    
    public static Boolean insertCodeInFile(File fileFromInsert, File fileToInsert, String lineAfterToInsert, String codeToInsert) {
        String importFileFrom = calculateImport(fileFromInsert, fileToInsert);
        if (importFileFrom != null) {
            if (insertCodeInNecessaryPlace(fileToInsert, "import ", importFileFrom))
                CustomLogger.writeLog(LogType.INFO, "Import was added" + importFileFrom);
        }
        return insertCodeInNecessaryPlace(fileToInsert, lineAfterToInsert, codeToInsert);
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
