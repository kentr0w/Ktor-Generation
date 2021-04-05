package Gradle;

import Handler.Core;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGradleProjectTest {

    private static List<String> gradleFiles = Arrays.asList("gradle-wrapper.properties",
            "build.gradle", "gradlew.bat", "gradlew", "settings.gradle", "Application.kt",
            "application.conf", "logback.xml");

    @Test
    private void testFilesGeneration() {
        Core core = new Core();
        core.start();
        try {
            List<String> paths = Files
                    .walk(Path.of("user"))
                    .filter(path -> path.toFile().isFile())
                    .map( path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            for (String e: paths) {
                System.out.println(e);
            }
            Assertions.assertEquals(gradleFiles, paths);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
