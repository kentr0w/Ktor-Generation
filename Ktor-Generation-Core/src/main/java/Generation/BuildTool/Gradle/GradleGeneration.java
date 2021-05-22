package Generation.BuildTool.Gradle;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.BuildToolGeneration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static Constant.Constant.GRADLE_BUILD_PATH;

public class GradleGeneration extends BuildToolGeneration {
    
    public GradleGeneration(String projectFolder) {
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate gradle's files");
        final Boolean[] isCopySuccessful = {true};
        Stream.of("build.gradle.tmp", "gradle.properties.tmp", "settings.gradle.tmp").forEach(it -> {
            InputStream gradleStream = GradleGeneration.class.getClassLoader().getResourceAsStream(GRADLE_BUILD_PATH + it);
            isCopySuccessful[0] = Replication.copyInputStream(gradleStream, projectFolder, it) && isCopySuccessful[0];
            try {
                gradleStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
        });
        if (isCopySuccessful[0]) {
            CustomLogger.writeLog(LogType.INFO, "Gradle's files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with gradle's files creation");
        }
        return isCopySuccessful[0];
    }
}
