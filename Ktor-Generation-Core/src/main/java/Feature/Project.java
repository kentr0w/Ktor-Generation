package Feature;

import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;

public class Project extends FeatureObject {
    
    private Global global;
    
    public Project() {
        super("project");
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
