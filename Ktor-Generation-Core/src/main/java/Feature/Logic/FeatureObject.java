package Feature.Logic;

public abstract class FeatureObject implements IFeature {
    protected String name;
    protected String path;
    
    public FeatureObject(String name) {
        this.name = name;
        System.out.println(this.name);
        Features features = Features.getInstance();
        features.addFeature(this);
    }
}
