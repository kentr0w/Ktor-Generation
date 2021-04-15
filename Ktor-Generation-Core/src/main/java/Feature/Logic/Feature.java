package Feature.Logic;

import java.util.ArrayList;
import java.util.List;

public class Feature<T> {
    
    private static Feature INSTANCE = null;
    
    public List<T> getFeature() {
        return features;
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
