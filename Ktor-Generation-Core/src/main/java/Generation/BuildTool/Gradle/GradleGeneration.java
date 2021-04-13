package Generation.BuildTool.Gradle;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.BuildGeneration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static Constant.Constant.GRADLE_BUILD_PATH;
import static Constant.Constant.SRC_BUILD_PATH;

public class GradleGeneration extends BuildGeneration {
    
    public GradleGeneration(String projectFolder) {
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate build tool's files");
        Boolean isBuildsFilesCreated = Replication.copyDirectory(GRADLE_BUILD_PATH, projectFolder);
        Boolean isSourcesFilesCreated = Replication.copyDirectory(SRC_BUILD_PATH, projectFolder);
        if (isBuildsFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Build's files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with build's files creation");
        }
        if (isSourcesFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Sources files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with sources files creation");
        }
        return isBuildsFilesCreated && isSourcesFilesCreated;
    }
}
