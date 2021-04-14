package Feature.CoreFeatures;

import Feature.CoreFeatures.Global.Global;
import Feature.CoreFeatures.routing.RoutingFeature;
import Feature.Logic.FeatureObject;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Project {
    
    private static final Logger logger = Logger.getLogger(Project.class);
    
    private Global global;
    private Features features;
    
    public Project() {
    
    }
    
    public Features getFeatures() {
        return features;
    }
    
    public void setFeatures(Features features) {
        this.features = features;
    }
    
    public Global getGlobal() {
        return global;
    }
    
    public void setGlobal(Global global) {
        this.global = global;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                '}';
    }
}
