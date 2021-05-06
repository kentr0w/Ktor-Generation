package Constant;

import Feature.CoreFeatures.Global.BuildTool;

import java.io.InputStream;

public class Constant {
    private static ClassLoader resources = Constant.class.getClassLoader();
    public static final InputStream CONFIG_PATH = resources.getResourceAsStream("config.yaml");
    public static final InputStream FILES_TREE_PATH = resources.getResourceAsStream("project.tr");
    public static final String GRADLE_BUILD_PATH = "template/gradle_build/";
    public static final String MAVEN_BUILD_PATH = "template/maven_build/";
    public static final String SRC_BUILD = "template/src_build/src/";
    public static final String RESOURCES_BUILD = "template/src_build/resources/";
    public static final BuildTool BUILD_TOOL = BuildTool.Gradle;
    public static final String GROUP = "com.example";
    public static final String VERSION = "0.0.1-SNAPSHOT";
    public static final String KTOR_VERSION = "1.5.0";
    public static final String KOTLIN_VERSION = "1.4.21.";
    public static final String PORT = "8080";
    public static final String MAIN_FUN = "fun Application.module(testing: Boolean = false) {";
}
