package Feature.Logic;

import Feature.CoreFeatures.Global.Global;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Feature<T> {
    
    private static Feature INSTANCE = null;
    
    public List<T> getFeature() {
        return features;
    }
    
    public List<String> getProjectPathFromGlobal() {
        Global global = (Global) features
                .stream()
                .filter(it -> (((FeatureObject) it).id.equals("global")))
                .collect(Collectors.toList())
                .get(0);
        
        return Arrays.asList((global.getProjectPath() + File.separator + "src").split(File.separator));
    }
    
    private List<T> features;
    
    public void addFeature(T feature) {
        features.add(feature);
    }
    
    private Feature() {
        this.features = new ArrayList<>();
    }
    
    public static Feature getInstance() {
        if (INSTANCE == null) {
            synchronized (Feature.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Feature();
                }
            }
        }
        return INSTANCE;
    }
}
