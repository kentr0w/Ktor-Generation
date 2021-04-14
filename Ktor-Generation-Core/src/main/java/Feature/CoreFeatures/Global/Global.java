package Feature.CoreFeatures.Global;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Constant.Constant.*;
import static Constant.Constant.BUILD_TOOL;

public class Global extends FeatureObject {
    
    private String folder;
    private String projectName;
    private String projectPath;
    private BuildTool buildType;
    private String group;
    private String version;
    private String ktorVersion;
    private String kotlinVersion;
    private String port;
    
    public Global() {
        super("global");
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
        return (folder + File.separator + projectName);
    }
    
    public String getFolder() {
        return folder;
    }
    
    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    @Override
    public void start() {
        CustomLogger.writeLog(LogType.INFO, "Starting to add major information");
        List<String> files = new ArrayList<>(Arrays.asList("resources/application.conf", "resources/application.conf", "src/Application.kt"));
        List<String> hashText = Arrays.asList("group", "port", "group", "projectName", "group", "version", "ktorVersion", "kotlinVersion");
        List<String> fields = Arrays.asList(this.group, this.port, this.group, this.projectName, this.group, this.version, this.ktorVersion, this.kotlinVersion);
        if(getBuildType() == BuildTool.Gradle) {
            files.addAll(Arrays.asList("settings.gradle", "build.gradle", "build.gradle", "gradle.properties", "gradle.properties"));
        } else {
            files.addAll(Collections.nCopies(hashText.size(), "pom.xml"));
        }
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
    public String toString() {
        return "Global{";
    }
    
    @Override
    public String getId() {
        return super.id;
    }
}
