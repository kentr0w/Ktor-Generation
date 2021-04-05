import Handler.Core;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    
    @Test
    public void testFilesGeneration() {
        Core core = new Core();
        core.start();
        try {
            List<String> paths = Files
                    .walk(Path.of("user"))
                    .filter(path -> path.toFile().isFile())
                    .map( path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            Assertions.assertTrue(paths.containsAll(gradleFiles));
            Assertions.assertTrue(gradleFiles.containsAll(paths));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
