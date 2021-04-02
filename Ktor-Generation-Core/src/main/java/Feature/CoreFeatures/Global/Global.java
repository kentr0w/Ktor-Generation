package Feature.CoreFeatures.Global;


import Feature.Logic.FeatureObject;

public class Global extends FeatureObject {
    
    private String projectName;
    private String buildType;
    
    public Global() {
        super("global");
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getBuildType() {
        return buildType;
    }
    
    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }
    
    @Override
    public String toString() {
        return "Global{" +
                "projectName='" + projectName + '\'' +
                ", buildType='" + buildType + '\'' +
                '}';
    }
    
    @Override
    public void start() {
    
    }
    
    @Override
    public String getName() {
        return super.name;
    }
}
