package Reader;

import Feature.CoreFeatures.Project;
import Generation.Project.Tree;

import java.io.InputStream;

public class FileReader {
    private ConfigReader configReader;
    private ProjectReader projectReader;
    
    public FileReader(InputStream configFeatureStream, InputStream configProjectStream) {
        this.configReader = new ConfigReader(configFeatureStream);
        this.projectReader = new ProjectReader(configProjectStream);
    }
    
    public Project readConfiguration() {
        return this.configReader.read();
    }
    
    public Tree readProject() {
        return this.projectReader.read();
    }
}
