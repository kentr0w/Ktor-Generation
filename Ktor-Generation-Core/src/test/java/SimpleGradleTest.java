import Generation.BuildTool.Gradle.GradleGeneration;
import Handler.Core;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGradleTest {
    
    private static List<String> gradleFiles = Arrays.asList("gradle-wrapper.properties",
            "build.gradle", "gradlew.bat", "gradlew", "settings.gradle", "Application.kt",
            "application.conf", "logback.xml");
    
    @BeforeEach
    public void removeUserFolder() {
        try {
            FileUtils.deleteDirectory(new File("user"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    @Test
    public void testFilesGeneration() {
        GradleGeneration gradleGeneration = new GradleGeneration("user");
        Boolean result = gradleGeneration.generate();
        Assertions.assertTrue(result);
        try {
            List<String> paths = Files
                    .walk(Path.of("user"))
                    .filter(path -> path.toFile().isFile())
                    .map( path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            //Assertions.assertTrue(paths.containsAll(gradleFiles));
            //Assertions.assertTrue(gradleFiles.containsAll(paths));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
