package Constant;

public class Constant {
    private static ClassLoader resources = Constant.class.getClassLoader();
    public static final String CONFIG_PATH = resources.getResource("config.yaml").getPath();
    public static final String FILES_TREE_PATH = resources.getResource("project.tr").getPath();
    public static final String GRADLE_BUILD_PATH = "template/gradle_build";
    public static final String SRC_BUILD_PATH = "template/resources_build";
}
