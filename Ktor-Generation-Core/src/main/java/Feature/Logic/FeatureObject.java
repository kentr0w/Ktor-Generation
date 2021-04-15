package Feature.Logic;

public abstract class FeatureObject implements IFeature {
    protected String id;
    protected String path;
    private Feature features = Feature.getInstance();
    
    public FeatureObject(String id) {
        this.id = id;
        features.addFeature(this);
    }
}
