package Feature.CoreFeatures.Controller;

import Feature.Logic.FeatureObject;

public class ControllerFeature extends FeatureObject {
    
    public ControllerFeature() {
        super("controller");
        //this.name = "controller";
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
        return "ControllerFeature{" +
                "name='" + name + '\'' +
                '}';
    }
}
