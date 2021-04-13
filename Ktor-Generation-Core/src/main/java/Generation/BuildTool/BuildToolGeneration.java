package Generation.BuildTool;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;

import java.io.IOException;

import static Constant.Constant.GRADLE_BUILD_PATH;
import static Constant.Constant.SRC_BUILD_PATH;

public abstract class BuildToolGeneration {
    protected String projectFolder;
    
    public BuildToolGeneration(String projectPath) {
        this.projectFolder = projectPath;
    }
    
    public abstract Boolean generate();
}
