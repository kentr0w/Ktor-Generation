package Feature.Logic;

import Feature.Features;

public abstract class FeatureObject implements IFeature {
    protected String name;
    
    public FeatureObject(String name) {
        this.name = name;
        System.out.println(this.name);
        Features features = Features.getInstance();
        features.addFeature(this);
    }
}
