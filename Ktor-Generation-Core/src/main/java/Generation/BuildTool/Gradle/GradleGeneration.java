package Generation.BuildTool.Gradle;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.BuildToolGeneration;

import static Constant.Constant.GRADLE_BUILD_PATH;
import static Constant.Constant.SRC_BUILD_PATH;

public class GradleGeneration extends BuildToolGeneration {
    
    public GradleGeneration(String projectFolder) {
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate gradle's files");
        Boolean isGradleFilesCreated = Replication.copyDirectory(GRADLE_BUILD_PATH, projectFolder);
        Boolean isSourcesFilesCreated = Replication.copyDirectory(SRC_BUILD_PATH, projectFolder);
        if (isGradleFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Gradle's files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with gradle's files creation");
        }
        if (isSourcesFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Sources files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with sources files creation");
        }
        return isGradleFilesCreated && isSourcesFilesCreated;
    }
}
