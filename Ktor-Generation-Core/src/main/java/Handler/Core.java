package Handler;

import Copy.LogType;
import Copy.CustomLogger;
import Feature.CoreFeatures.Global.Global;
import Feature.Logic.FeatureObject;
import Feature.Logic.Feature;
import Feature.CoreFeatures.Project;
import Feature.Logic.IFeature;
import Generation.BuildTool.BuildToolGeneration;
import Generation.BuildTool.Gradle.GradleGeneration;
import Generation.BuildTool.Maven.MavenGeneration;
import Generation.Project.FileTreeGeneration;
import Reader.FileReader;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

import static Constant.Constant.CONFIG_PATH;
import static Constant.Constant.FILES_TREE_PATH;

public class Core {

    private FileReader fileReader;

    public Core() {
        InputStream featureConfigStream = findConfigFile("config.yaml", CONFIG_PATH);
        InputStream fileTreeConfigStream = findConfigFile("project.tr", FILES_TREE_PATH);
        this.fileReader = new FileReader(featureConfigStream, fileTreeConfigStream);
    }

    private InputStream findConfigFile(String fileName, InputStream defaultValue) {
        InputStream configFileStream = defaultValue;
        File configFile = new File(fileName);
        if (configFile.exists()) {
            try {
                configFileStream = new FileInputStream(configFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return configFileStream;
    }
    
    public Boolean start() {
        Project project = fileReader.readConfiguration();
        if (project == null) {
            // TODO How to pass information about error in config file to web?
            System.out.println("Can't read configuration file");
            System.exit(0);
        }
        Global global = project.getGlobal();
        Path newPath = generateProjectDir(global.getProjectPath());
        if (newPath == null)
            System.exit(0); // TODO what should we do?
        BuildToolGeneration buildGeneration = null;
        switch (global.getBuildType()) {
            case Gradle:
                buildGeneration = new GradleGeneration(newPath.toString());
                break;
            case Maven:
                buildGeneration = new MavenGeneration(newPath.toString());
                break;
        }
        buildGeneration.generate();
        FileTreeGeneration fileTreeGeneration = new FileTreeGeneration(fileReader.readProject(), global.getProjectPath());
        Boolean result = fileTreeGeneration.generate();
        fileTreeGeneration.insertPackageInKtFiles(global.getGroup());
        runAllFeatures();
        return result;
    }
    
    private void runAllFeatures() {
        CustomLogger.writeLog(LogType.INFO, "Starting to implement all features");
        List<? extends FeatureObject> allFeatures = Feature.getInstance().getFeature();
        allFeatures.forEach(IFeature::start);
    }
    
    private Path generateProjectDir(String projectFolderPath) {
        try {
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx"); // TODO check on Windows!
            Path path = Files.createDirectories(Path.of(projectFolderPath), PosixFilePermissions.asFileAttribute(perms));
            CustomLogger.initPath(projectFolderPath);
            CustomLogger.writeLog(LogType.INFO, "Empty folder was created successfully");
            CustomLogger.writeLog(LogType.INFO, "Log file was created");
            return path;
        } catch (IOException exception) {
            return null;
        }
    }
}
