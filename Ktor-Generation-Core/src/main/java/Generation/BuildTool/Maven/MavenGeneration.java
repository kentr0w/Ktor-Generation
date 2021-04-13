package Generation.BuildTool.Maven;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.BuildToolGeneration;

import static Constant.Constant.*;

public class MavenGeneration extends BuildToolGeneration {

    public MavenGeneration(String projectFolder){
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate maven's files");
        Boolean isMavenFilesCreated = Replication.copyDirectory(MAVEN_BUILD_PATH, projectFolder);
        Boolean isSourcesFilesCreated = Replication.copyDirectory(SRC_BUILD_PATH, projectFolder);
        if (isMavenFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Maven's files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with maven's files creation");
        }
        if (isSourcesFilesCreated){
            CustomLogger.writeLog(LogType.INFO, "Sources files was created successfully");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with sources files creation");
        }
        return isMavenFilesCreated && isSourcesFilesCreated;
    }
}
