package Feature;

import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;

public class Project extends FeatureObject {
    
    private String id;
    private Global global;
    
    public Project() {
        super("project");
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Global getGlobal() {
        return global;
    }
    
    public void setGlobal(Global global) {
        this.global = global;
    }
    
    @Override
    public void start() {
    
    }
    
    @Override
    public String getName() {
        return super.name;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "global=" + super.name +
                '}';
    }
}
