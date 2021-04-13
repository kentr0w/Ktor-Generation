package Generation.BuildTool.Maven;

import Generation.BuildTool.BuildGeneration;

public class MavenGeneration extends BuildGeneration {

    public MavenGeneration(String projectFolder){
        super(projectFolder);
    }

    @Override
    public Boolean generate() {
        return false;
    }
}
