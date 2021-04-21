package Feature.CoreFeatures.db.entity.routes;

import Feature.CoreFeatures.routing.RouteDetail;

import java.util.List;

public class Route {
    private List<StandardRoute> standardRoutes;
    private List<RouteDetail> routeDetail;
    
    public Route() {}
    
    public List<RouteDetail> getRouteDetail() {
        return routeDetail;
    }
    
    public void setRouteDetail(List<RouteDetail> routeDetail) {
        this.routeDetail = routeDetail;
    }
    
    public List<StandardRoute> getStandardRoutes() {
        return standardRoutes;
    }
    
    public void setStandardRoutes(List<StandardRoute> standardRoutes) {
        this.standardRoutes = standardRoutes;
    }
}
