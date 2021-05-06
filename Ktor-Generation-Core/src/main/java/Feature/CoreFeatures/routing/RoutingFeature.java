package Feature.CoreFeatures.routing;

import Copy.CustomLogger;
import Copy.Insertion;
import Copy.LogType;
import Feature.CoreFeatures.Global.Global;
import Feature.Logic.Feature;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static Constant.Constant.MAIN_FUN;

public class RoutingFeature extends FeatureObject {
    
    private static final String requestTmp = "template/routing_build/request.tmp";
    private static final String routeTmp = "template/routing_build/route.tmp";
    private static final String applicationTmp = "template/routing_build/application.tmp";
    private static final String importTmp = "template/routing_build/import.tmp";
    
    private String path;
    private String name;
    private List<RouteDetail> routeDetail;
    
    public RoutingFeature() {
        super("routing");
    }
    
    public String getPath() {
        String missPartOfPath = super.calculatePath(this.path);
        if (!missPartOfPath.equals(""))
            this.setPath(missPartOfPath + File.separator + this.path);
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
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
        StringBuilder allRoutes = new StringBuilder();
        for (RouteDetail route: routeDetail) {
            StringBuilder allRequestsForOneRoute = new StringBuilder();
            for(Request request: route.getRequests()) {
                InputStream requestStream = RoutingFeature.class.getClassLoader().getResourceAsStream(requestTmp);
                String codeAfterReplace = getCodeAfterReplace(requestStream, request.getHashList(), request.getTestList());
                try {
                    requestStream.close();
                } catch (IOException exception) {
                    CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                }
                if (codeAfterReplace == null) {
                    CustomLogger.writeLog(LogType.ERROR, "Couldn't create request " + request.getRequestUrl());
                } else {
                    CustomLogger.writeLog(LogType.INFO, "Created request " + request.getRequestUrl());
                    allRequestsForOneRoute.append(codeAfterReplace);
                }
            }
            List<String> text = Arrays.asList(route.getUrl(), allRequestsForOneRoute.toString());
            InputStream routeStream = RoutingFeature.class.getClassLoader().getResourceAsStream(routeTmp);
            String codeAfterReplace = getCodeAfterReplace(routeStream, route.getHash() , text);
            try {
                routeStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
            if (codeAfterReplace == null) {
                CustomLogger.writeLog(LogType.ERROR, "Couldn't create route " + route.getUrl());
            } else {
                CustomLogger.writeLog(LogType.INFO, "Create route " + route.getUrl());
                allRoutes.append(codeAfterReplace);
            }
        }
        InputStream importsStream = RoutingFeature.class.getClassLoader().getResourceAsStream(importTmp);
        if(Insertion.addImports(new File(this.getPath()), importsStream)) {
            CustomLogger.writeLog(LogType.INFO, "Import was added");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Import wasn't added");
        }
        try{
            importsStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        InputStream applicationStream = RoutingFeature.class.getClassLoader().getResourceAsStream(applicationTmp);
        Boolean isCodeCopy = duplicateCodeFromTemplateToFile(applicationStream, this.getPath());
        try{
            applicationStream.close();
        } catch (Exception exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        if (isCodeCopy) {
            Boolean isFeatureImplemented = replaceTextByHash(this.getPath(), DigestUtils.sha256Hex("routeName"), this.name)
                    && replaceTextByHash(this.getPath(), DigestUtils.sha256Hex("route"), allRoutes.toString());
            if (isFeatureImplemented) {
                CustomLogger.writeLog(LogType.INFO, "Routing feature implemented");
                String pathToApplicationFile = getSrcPath(this.getPath()) + File.separator + "Application.kt"; // may be constant?
                if (Insertion.insertCodeWithImportInFile(new File(this.getPath()), new File(pathToApplicationFile), MAIN_FUN, this.name + "()")) {
                    CustomLogger.writeLog(LogType.INFO, "Added route to main");
                } else {
                    CustomLogger.writeLog(LogType.ERROR, "Couldn't added to main");
                }
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
