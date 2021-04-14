package Feature.CoreFeatures.Controller;

import Feature.Logic.FeatureObject;

public class ControllerFeature extends FeatureObject {
    
    public ControllerFeature() {
        super("controller");
    }
    
    @Override
    public void start() {
    }
    
    @Override
    public String getId() {
        return super.id;
    }
    
    @Override
    public String toString() {
        return "ControllerFeature{" +
                "name='" + id + '\'' +
                '}';
    }
}
