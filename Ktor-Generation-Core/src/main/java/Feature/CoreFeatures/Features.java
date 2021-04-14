package Feature.CoreFeatures;

import Feature.CoreFeatures.routing.RoutingFeature;

import java.util.List;

public class Features {
    
    private List<RoutingFeature> routes;
    
    public Features() {}
    
    public List<RoutingFeature> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<RoutingFeature> routes) {
        this.routes = routes;
    }
}
