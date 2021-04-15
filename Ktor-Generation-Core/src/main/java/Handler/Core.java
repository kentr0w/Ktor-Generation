package Handler;

import Copy.LogType;
import Copy.CustomLogger;
import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;
import Feature.Logic.Feature;
import Feature.CoreFeatures.Project;
import Generation.BuildTool.BuildToolGeneration;
import Generation.BuildTool.Gradle.GradleGeneration;
import Generation.BuildTool.Maven.MavenGeneration;
import Generation.Project.ProjectGeneration;
import Reader.FileReader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

import static Constant.Constant.CONFIG_PATH;
import static Constant.Constant.FILES_TREE_PATH;

public class Core {
    
    private static final Logger logger = Logger.getLogger(Core.class);
    private FileReader fileReader;
    
    public Core(String configPath, String filesTreePath) {
        this.fileReader = new FileReader(configPath, filesTreePath);
        logger.info("Core was created");
        logger.info("Path to configuration file is: " + configPath);
        logger.info("Path to files structure description is: " + filesTreePath);
    }

    public Core() {
        this.fileReader = new FileReader(CONFIG_PATH, FILES_TREE_PATH);
        logger.info("Core was created");
        logger.info("Path to configuration file isn't pass, will be use default: " + CONFIG_PATH);
        logger.info("Path to files structure description isn't pass, will be use default: " + FILES_TREE_PATH);
    }
    
    public Boolean start() {
        logger.info("Starting to generate user's project");
        Project project = fileReader.readConfiguration();
        Global global = project.getGlobal();
        if (project == null) {
            // TODO How to pass information about error in config file to web?
            System.out.println("Can't read configuration file");
            System.exit(0);
        }
        if (!generateProjectDir(global.getProjectPath()))
            System.exit(0); // TODO what should we do?
        BuildToolGeneration buildGeneration = null;
        switch (global.getBuildType()) {
            case Gradle:
                buildGeneration = new GradleGeneration(global.getProjectPath());
                break;
            case Maven:
                buildGeneration = new MavenGeneration(global.getProjectPath());
                break;
        }
        buildGeneration.generate();
        
        ProjectGeneration projectGeneration = new ProjectGeneration(fileReader, global.getProjectPath());
        Boolean result = projectGeneration.generate();
        projectGeneration.insertPackageInsKtFiles(global.getGroup());
        runAllFeatures();
        return result;
    }
    
    private void runAllFeatures() {
        CustomLogger.writeLog(LogType.INFO, "Starting to implement all features");
        List<? extends FeatureObject> allFeatures = Feature.getInstance().getFeature();
        allFeatures.forEach(feature -> feature.start());
    }
    
    private Boolean generateProjectDir(String projectFolderPath) {
        try {
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx"); // TODO check on Windows!
            Path path = Files.createDirectories(Path.of(projectFolderPath), PosixFilePermissions.asFileAttribute(perms));
            logger.info("Folder for users project was created: " + path.toString());
            CustomLogger.initPath(projectFolderPath);
            CustomLogger.writeLog(LogType.INFO, "Empty folder was created successfully");
            CustomLogger.writeLog(LogType.INFO, "Log file was created");
            return true;
        } catch (IOException exception) {
            logger.error("Error to generate folder for users project");
            return false;
        }
    }
}
