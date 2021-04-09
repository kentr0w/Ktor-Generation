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
        List<String> notCopiedFiles = new ArrayList<>();
        List<String> notCopiedDirs = new ArrayList<>();
        try {
            Files.walk(Path.of(templatePath))
                    .map(Path::toFile)
                    .forEach(file -> {
                        String realPath = file.getPath().substring(templatePath.length());
                        if (file.isDirectory()) {
                            try {
                                Files.createDirectories(Path.of(projectFolder + File.separator + realPath));
                            } catch (IOException exception) {
                                notCopiedDirs.add(realPath);
                            }
                        } else if (file.isFile()) {
                            String q = realPath;
                            q = q.substring(0, q.indexOf(".tmp"));
                            File newFile = new File(projectFolder + File.separator + q);
                            try {
                                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException exception) {
                                notCopiedFiles.add(realPath);
                            }
                        }
                    });
            if (!notCopiedFiles.isEmpty()) {
                // TODO log
            }
            return notCopiedFiles.isEmpty();
        } catch (IOException exception) {
            exception.printStackTrace();
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
