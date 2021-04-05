package Generation.BuildTool;

public abstract class BuildGeneration {
    protected String projectFolder;
    
    public BuildGeneration(String projectPath) {
        this.projectFolder = projectPath;
    }
    
    protected abstract boolean generate();
    protected abstract void initInfo();
    protected abstract void copyBuildToolTemplates();
}
