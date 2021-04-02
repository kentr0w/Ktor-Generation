package Feature;

import java.util.ArrayList;
import java.util.List;

public class Features<T> {
    
    private static Features INSTANCE = null;
    
    public List<T> getFeatures() {
        return features;
    }
    
    private List<T> features;
    
    public void addFeature(T feature) {
        features.add(feature);
    }
    
    private Features() {
        this.features = new ArrayList<>();
    }
    
    public static Features getInstance() {
        if (INSTANCE == null) {
            synchronized (Features.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Features();
                }
            }
        }
        return INSTANCE;
    }
}
