package Feature.CoreFeatures;

import Feature.CoreFeatures.entity.EntityFeature;
import Feature.CoreFeatures.routing.RoutingFeature;
import Feature.CoreFeatures.web.WebFeature;

import java.util.List;

public class Features {
    
    private List<RoutingFeature> routes;
    private List<EntityFeature> entities;
    private WebFeature web;
    
    public Features() {}

    public WebFeature getWeb() {
        return web;
    }

    public void setWeb(WebFeature web) {
        this.web = web;
    }

    public List<EntityFeature> getEntities() {
        return entities;
    }
    
    public void setEntities(List<EntityFeature> entities) {
        this.entities = entities;
    }
    
    public List<RoutingFeature> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<RoutingFeature> routes) {
        this.routes = routes;
    }
}
