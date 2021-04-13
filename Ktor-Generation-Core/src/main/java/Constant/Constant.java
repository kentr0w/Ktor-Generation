package Constant;

import Feature.CoreFeatures.Global.BuildTool;

public class Constant {
    private static ClassLoader resources = Constant.class.getClassLoader();
    public static final String CONFIG_PATH = resources.getResource("config.yaml").getPath();
    public static final String FILES_TREE_PATH = resources.getResource("project.tr").getPath();
    public static final String GRADLE_BUILD_PATH = "template/gradle_build";
    public static final String SRC_BUILD_PATH = "template/src_build";
    public static final BuildTool BUILD_TOOL = BuildTool.Gradle;
    public static final String GROUP = "com.example";
    public static final String VERSION = "0.0.1-SNAPSHOT";
    public static final String KTOR_VERSION = "1.5.0";
    public static final String KOTLIN_VERSION = "1.4.21.";
    public static final String PORT = "8080";
}
