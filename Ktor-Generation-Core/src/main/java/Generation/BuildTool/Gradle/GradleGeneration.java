package Generation.BuildTool.Gradle;

import Generation.BuildTool.BuildGeneration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static Constant.Constant.GRADLE_BUILD_PATH;

public class GradleGeneration extends BuildGeneration {
    
    public GradleGeneration(String projectFolder) {
        super(projectFolder);
    }
    
    @Override
    public boolean generate() {
        copyBuildToolTemplates();
        return true;
    }
    
    @Override
    protected void initInfo() {
        // TODO
    }
    
    @Override
    protected void copyBuildToolTemplates() {
        try {
            Files.walk(Path.of(GRADLE_BUILD_PATH))
                .map(Path::toFile)
                .forEach(file -> {
                    String realPath = file.getPath().substring("template/gradle_build".length());
                    if (file.isDirectory()) {
                        try {
                            Files.createDirectories(Path.of(super.projectFolder + File.separator + realPath));
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    } else if (file.isFile()) {
                        String q = realPath;
                        q = q.substring(0, q.indexOf(".tmp"));
                        File newFile = new File(super.projectFolder + File.separator + q);
                        System.out.println(newFile.getAbsolutePath());
                        try {
                            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                    });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
