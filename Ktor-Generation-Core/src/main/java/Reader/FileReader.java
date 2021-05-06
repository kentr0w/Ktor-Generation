package Reader;

import Feature.CoreFeatures.Project;
import Generation.Project.Tree;

import java.io.InputStream;

public class FileReader {
    private ConfigReader configReader;
    private FileTreeReader fileTreeReader;
    
    public FileReader(InputStream configFeatureStream, InputStream configProjectStream) {
        this.configReader = new ConfigReader(configFeatureStream);
        this.fileTreeReader = new FileTreeReader(configProjectStream);
    }
    
    public Project readConfiguration() {
        return this.configReader.read();
    }
    
    public Tree readProject() {
        return this.fileTreeReader.read();
    }
}
