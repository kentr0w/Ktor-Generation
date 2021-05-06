package Reader;

import Feature.CoreFeatures.Global.Global;
import Feature.CoreFeatures.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class ConfigReader {

    private static final Logger logger = Logger.getLogger(ConfigReader.class);
    private InputStream configFeatureStream;
    private ObjectMapper mapper;

    public InputStream getConfigFeatureStream() {
        return configFeatureStream;
    }
    
    public void setConfigFeatureStream(InputStream configFeatureStream) { this.configFeatureStream = configFeatureStream; }
    
    public ObjectMapper getMapper() {
        return mapper;
    }
    
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public ConfigReader(InputStream configFeatureStream) {
        this.configFeatureStream = configFeatureStream;
        mapper = new ObjectMapper(new YAMLFactory());
    }
    
    public Project read() {
        logger.info("Starting to read configuration file");
        try {
            Yaml yaml = new Yaml(new Constructor(Project.class));
            Project resultProject = yaml.load(this.configFeatureStream);
            Global global = resultProject.getGlobal();
            logger.info("Configuration file was read");
            if (global.getProjectName() != null || global.getFolder() != null) {
                logger.info("Configuration file was read successfully");
                return resultProject;
            }
            else {
                logger.info("The project name or project folder isn't specified in the file");
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("Error with configuration file");
            return null;
        }
    }
}
