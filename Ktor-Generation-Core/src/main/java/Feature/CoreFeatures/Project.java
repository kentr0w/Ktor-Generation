package Feature.CoreFeatures;

import Feature.CoreFeatures.Global.BuildTool;
import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.File;

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
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getKtorVersion() {
        return ktorVersion;
    }
    
    public void setKtorVersion(String ktorVersion) {
        this.ktorVersion = ktorVersion;
    }
    
    public String getKotlinVersion() {
        return kotlinVersion;
    }
    
    public void setKotlinVersion(String kotlinVersion) {
        this.kotlinVersion = kotlinVersion;
    }
    
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public BuildTool getBuildType() {
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
        logger.info("hehe");
        logger.error("hehee");
        if(this.buildType == BuildTool.Gradle) {
            replaceTextByHash(getRealFilePath("settings.gradle"), DigestUtils.sha256Hex("projectName"), this.projectName);
            replaceTextByHash(getRealFilePath("build.gradle"), DigestUtils.sha256Hex("group"), this.group);
            replaceTextByHash(getRealFilePath("build.gradle"), DigestUtils.sha256Hex("version"), this.version);
            replaceTextByHash(getRealFilePath("gradle.properties"), DigestUtils.sha256Hex("ktor_version"), this.ktorVersion);
            replaceTextByHash(getRealFilePath("gradle.properties"), DigestUtils.sha256Hex("kotlin_version"), this.kotlinVersion);
        } else {
        
        }
        replaceTextByHash(getRealFilePath("resources/application.conf"), DigestUtils.sha256Hex("group"), this.group);
        replaceTextByHash(getRealFilePath("resources/application.conf"), DigestUtils.sha256Hex("port"), this.port);
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
