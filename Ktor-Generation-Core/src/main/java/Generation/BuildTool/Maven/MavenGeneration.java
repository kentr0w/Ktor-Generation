package Generation.BuildTool.Maven;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.BuildToolGeneration;
import Generation.BuildTool.Gradle.GradleGeneration;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static Constant.Constant.*;

public class MavenGeneration extends BuildToolGeneration {

    public MavenGeneration(String projectFolder){
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate maven's files");
        final Boolean[] isCopySuccessful = {false};
        Stream.of("pom.xml.tmp").forEach(it -> {
            InputStream gradleStream = GradleGeneration.class.getClassLoader().getResourceAsStream(MAVEN_BUILD_PATH + it);
            isCopySuccessful[0] = Replication.copyInputStream(gradleStream, projectFolder, it) && isCopySuccessful[0];
            try {
                gradleStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
        });
        if (isCopySuccessful[0]){
            CustomLogger.writeLog(LogType.INFO, "Maven's files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with maven's files creation");
        }
        return isCopySuccessful[0];
    }
}
