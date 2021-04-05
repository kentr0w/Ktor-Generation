package Reader;

import Feature.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static Constant.Constant.CONFIG_PATH;

public class ConfigReader {
    
    public String getConfigPath() {
        return configPath;
    }
    
    public void setConfigPath(String configPath) {
        if (configPath == null) {
            this.configPath = CONFIG_PATH;
        } else {
            this.configPath = configPath;
        }
    }
    
    public ObjectMapper getMapper() {
        return mapper;
    }
    
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    private String configPath;
    private ObjectMapper mapper;
    
    public ConfigReader(String configPath) {
        setConfigPath(configPath);
        mapper = new ObjectMapper(new YAMLFactory());
    }
    
    public Project read() {
        //mapper.findAndRegisterModules();
        try {
            return mapper.readValue(new File(configPath), Project.class);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
