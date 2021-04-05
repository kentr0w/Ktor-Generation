package Generation.BuildTool.Maven;

import Generation.BuildTool.BuildGeneration;

public class MavenGeneration extends BuildGeneration {

    public MavenGeneration(String projectFolder){
        super(projectFolder);
    }


    @Override
    protected boolean initInfo() {
        return true;
    }
}
