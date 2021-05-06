package Generation.BuildTool;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;

import java.io.IOException;

public abstract class BuildToolGeneration {
    protected String projectFolder;
    
    public BuildToolGeneration(String projectPath) {
        this.projectFolder = projectPath;
    }
    
    public abstract Boolean generate();
}
