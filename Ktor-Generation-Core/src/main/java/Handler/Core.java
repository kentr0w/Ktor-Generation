package Handler;

import Feature.Logic.FeatureObject;
import Feature.Features;
import Reader.ConfigReader;

import java.util.List;

public class Core {
    
    private String configPath;
    private ConfigReader configReader;
    
    public Core(String configPath) {
        this.configPath = configPath;
        this.configReader = new ConfigReader(this.configPath);
    }
    
    public void start() {
        configReader.read();
        Features<? extends FeatureObject> q = Features.getInstance();
        List<? extends FeatureObject> all = q.getFeatures();
        for(FeatureObject w: all) {
            System.out.println(w);
        }
    }
}
