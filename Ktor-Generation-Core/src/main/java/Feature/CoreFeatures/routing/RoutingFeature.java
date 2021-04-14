package Feature.CoreFeatures.routing;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RoutingFeature extends FeatureObject {
    
    private static final String requestTmp = "template/routing_build/request.tmp";
    private static final String routeTmp = "template/routing_build/route.tmp";
    private static final String applicationTmp = "template/routing_build/application.tmp";
    private static final String importTmp = "template/routing_build/import.tmp";
    
    private String name;
    private String file;
    private List<RouteDetail> routeDetail;
    
    public RoutingFeature() {
        super("routing");
    }
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<RouteDetail> getRouteDetail() {
        return routeDetail;
    }
    
    public void setRouteDetail(List<RouteDetail> routeDetail) {
        this.routeDetail = routeDetail;
    }
    
    @Override
    public void start() {
        CustomLogger.writeLog(LogType.INFO, "Starting implement " + this.name + " route");
        Boolean isImportDuplicated = duplicateCodeFromTemplateToFile(importTmp, this.file);
        if (isImportDuplicated)
            CustomLogger.writeLog(LogType.INFO, "Imports were added");
        else
            CustomLogger.writeLog(LogType.ERROR, "Imports weren't added");
        String allRoutes = "";
        for (RouteDetail route: routeDetail) {
            String allRequestsForOneRoute = "";
            for(Request request: route.getRequests()) {
                List<String> text = Arrays.asList(request.getPath(), request.getType().name().toLowerCase(Locale.ROOT));
                String codeAfterReplace = getCodeAfterReplace(requestTmp, request.getHashList(), text);
                if (codeAfterReplace == null) {
                    CustomLogger.writeLog(LogType.ERROR, "Couldn't create request " + request.getPath());
                } else {
                    CustomLogger.writeLog(LogType.INFO, "Created request " + request.getPath());
                    allRequestsForOneRoute += codeAfterReplace;
                }
            }
            List<String> text = Arrays.asList(route.getPath(), allRequestsForOneRoute);
            String codeAfterReplace = getCodeAfterReplace(routeTmp, route.getHash() , text);
            if (codeAfterReplace == null) {
                CustomLogger.writeLog(LogType.ERROR, "Couldn't create route " + route.getPath());
            } else {
                CustomLogger.writeLog(LogType.INFO, "Create route " + route.getPath());
                allRoutes += codeAfterReplace;
            }
        }
        Boolean isCodeCopy = duplicateCodeFromTemplateToFile(applicationTmp, this.file);
        if (isCodeCopy) {
            // also should be name
            Boolean result = replaceTextByHash(this.file, DigestUtils.sha256Hex("routeName"), this.name)
                    && replaceTextByHash(this.file, DigestUtils.sha256Hex("route"), allRoutes);
            if (result) {
                // TODO Add this routing to Application.module() {}
                CustomLogger.writeLog(LogType.INFO, "Routing feature implemented");
            } else {
                // should delete code, but how?
                CustomLogger.writeLog(LogType.ERROR, "Couldn't replace hash to value in routing code");
            }
        } else {
            // should delete code, but how?
            CustomLogger.writeLog(LogType.ERROR, "Couldn't duplicate routing code");
        }
    }
    
    @Override
    public String getId() {
        return super.id;
    }
}
