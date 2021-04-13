package Reader;

import Feature.CoreFeatures.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static Constant.Constant.CONFIG_PATH;

public class ConfigReader {

    private static final Logger logger = Logger.getLogger(ConfigReader.class);
    private String configPath;
    private ObjectMapper mapper;

    public String getConfigPath() {
        return configPath;
    }
    
    public void setConfigPath(String configPath) { this.configPath = configPath; }
    
    public ObjectMapper getMapper() {
        return mapper;
    }
    
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public ConfigReader(String configPath) {
        this.configPath = configPath;
        mapper = new ObjectMapper(new YAMLFactory());
    }
    
    public Project read() {
        //mapper.findAndRegisterModules();
        logger.info("Starting to read configuration file");
        try {
            Project resultProject = mapper.readValue(new File(configPath), Project.class);
            logger.info("Configuration file was read");
            if (resultProject.getProjectName() != null || resultProject.getProjectFolder() != null) {
                logger.info("Configuration file was read successfully");
                return resultProject;
            }
            else {
                logger.info("The project name or project folder isn't specified in the file");
                return null;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("Error with configuration file");
            return null;
        }
    }
}
