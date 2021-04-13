package Reader;

import Feature.CoreFeatures.Project;
import Generation.Project.Tree;

public class FileReader {
    private ConfigReader configReader;
    private ProjectReader projectReader;
    
    public FileReader(String configPath, String projectPath) {
        this.configReader = new ConfigReader(configPath);
        this.projectReader = new ProjectReader(projectPath);
    }
    
    public Project readConfiguration() {
        return  this.configReader.read();
    }
    
    public Tree readProject() {
        return this.projectReader.read();
    }
}
