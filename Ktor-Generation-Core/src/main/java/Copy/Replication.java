package Copy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static Constant.Constant.GRADLE_BUILD_PATH;

public class Replication {

    public static Boolean copyDirectory(String templatePath, String projectFolder) {
        List<String> notCopied = new ArrayList<>();
        try {
            Files.walk(Path.of(templatePath))
                    .map(Path::toFile)
                    .forEach(file -> {
                        String realPath = file.getPath().substring(templatePath.length());
                        if (file.isDirectory()) {
                            try {
                                Files.createDirectories(Path.of(projectFolder + File.separator + realPath));
                            } catch (IOException exception) {
                                notCopied.add(realPath);
                            }
                        } else if (file.isFile()) {
                            String filePathWithoutSuffix = realPath;
                            filePathWithoutSuffix = filePathWithoutSuffix.substring(0, filePathWithoutSuffix.indexOf(".tmp"));
                            File newFile = new File(projectFolder + File.separator + filePathWithoutSuffix);
                            try {
                                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException exception) {
                                notCopied.add(realPath);
                            }
                        }
                    });
            if (!notCopied.isEmpty()) {
                notCopied.forEach(notCopiedFile -> {
                    CustomLogger.writeLog(LogType.ERROR, notCopiedFile + " file wasn't copied");
                });
            }
            return notCopied.isEmpty();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            return false;
        }
    }
    
    public static Boolean generateSrc(String path, Boolean isDir) {
        File file = new File(path);
        if (file.exists())
            return true;
        try {
            if (isDir) {
                file.mkdirs();
            } else {
                file.createNewFile();
            }
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
