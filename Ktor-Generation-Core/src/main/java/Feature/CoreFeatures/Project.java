package Feature.CoreFeatures;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.CoreFeatures.Global.BuildTool;
import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Constant.Constant.*;

public class Project extends FeatureObject {
    
    private static final Logger logger = Logger.getLogger(Project.class);
    
    private String projectFolder;
    private String projectName;
    private String projectPath;
    private BuildTool buildType;
    private String group;
    private String version;
    private String ktorVersion;
    private String kotlinVersion;
    private String port;
    private Global global;
    
    public Project() {
        super("project");
    }
    
    public String getPort() {
        if (this.port == null)
            this.port = PORT;
        return this.port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getKtorVersion() {
        if (this.ktorVersion == null)
            this.ktorVersion = KTOR_VERSION;
        return this.ktorVersion;
    }
    
    public void setKtorVersion(String ktorVersion) {
        this.ktorVersion = ktorVersion;
    }
    
    public String getKotlinVersion() {
        if (this.kotlinVersion == null)
            this.kotlinVersion = KOTLIN_VERSION;
        return this.kotlinVersion;
    }
    
    public void setKotlinVersion(String kotlinVersion) {
        this.kotlinVersion = kotlinVersion;
    }
    
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
    
    public String getGroup() {
        if (this.group == null)
            this.group = GROUP;
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    public String getVersion() {
        if (this.version == null)
            this.version = VERSION;
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public BuildTool getBuildType() {
        if (this.buildType == null) {
            CustomLogger.writeLog(LogType.INFO, "Build tool isn't present in config file");
            setBuildType(BUILD_TOOL);
        }
        return buildType;
    }
    
    public void setBuildType(BuildTool buildType) {
        this.buildType = buildType;
    }
    
    public String getProjectPath() {
        return (projectFolder + File.separator + projectName);
    }
    
    public String getProjectFolder() {
        return projectFolder;
    }
    
    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public Global getGlobal() {
        return global;
    }
    
    public void setGlobal(Global global) {
        this.global = global;
    }
    
    @Override
    public void start() {
        CustomLogger.writeLog(LogType.INFO, "Starting to add major information");
        List<String> files = null;
        List<String> hashText = null;
        List<String> fields = null;
        if(getBuildType() == BuildTool.Gradle) {
            files = new ArrayList<>(Arrays.asList("settings.gradle", "build.gradle", "build.gradle", "gradle.properties", "gradle.properties"));
            hashText = new ArrayList<>(Arrays.asList("projectName", "group", "version", "ktorVersion", "kotlinVersion"));
            fields = new ArrayList<>(Arrays.asList(this.projectName, this.group, this.version, this.ktorVersion, this.kotlinVersion));
        } else {
            // TODO maven files
        }
        List<String> resources = Arrays.asList("resources/application.conf", "resources/application.conf");
        List<String> hashResources = Arrays.asList("group", "port");
        List<String> fieldsResources = Arrays.asList(this.group, this.port);
        files.addAll(resources);
        hashText.addAll(hashResources);
        fields.addAll(fieldsResources);
        replaceAllByHash(files, hashText, fields);
    }

    private void replaceAllByHash(List<String> files, List<String> text, List<String> fields) {
        for (int i = 0; i < fields.size(); i++) {
            String gradlePath = getRealFilePath(files.get(i));
            CustomLogger.writeLog(LogType.INFO, "Add `" + text.get(i) + "` to " + gradlePath);
            Boolean isTextReplaced = replaceTextByHash(gradlePath, DigestUtils.sha256Hex(text.get(i)), fields.get(i));
            if (isTextReplaced) {
                CustomLogger.writeLog(LogType.INFO, "Information was added");
            } else {
                CustomLogger.writeLog(LogType.ERROR, "information wasn't added");
            }
        }
    }
    
    private String getRealFilePath(String gradleFile) {
        return getProjectPath() + File.separator + gradleFile;
    }
    
    @Override
    public String getName() {
        return super.name;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "global=" + super.name +
                '}';
    }
}
