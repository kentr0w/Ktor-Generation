package Feature.CoreFeatures;

import Feature.CoreFeatures.db.DataBaseFeature;
import Feature.CoreFeatures.routing.RoutingFeature;
import Feature.CoreFeatures.web.WebFeature;

import java.util.List;

public class Features {
    
    private List<RoutingFeature> routes;
    private WebFeature web;
    private DataBaseFeature database;
    
    public Features() {}
    
    public DataBaseFeature getDatabase() {
        return database;
    }
    
    public void setDatabase(DataBaseFeature database) {
        this.database = database;
    }
    
    public WebFeature getWeb() {
        return web;
    }

    public void setWeb(WebFeature web) {
        this.web = web;
    }
    
    public List<RoutingFeature> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<RoutingFeature> routes) {
        this.routes = routes;
    }
}
