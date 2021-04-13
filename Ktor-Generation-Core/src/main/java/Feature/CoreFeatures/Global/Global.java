package Feature.CoreFeatures.Global;


import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

public class Global extends FeatureObject {
    
    private String port;
    
    public Global() {
        super("global");
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    @Override
    public String toString() {
        return "Global{" +
                "port='" + port + '\'' +
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
