package Handler;

import Copy.LogType;
import Copy.CustomLogger;
import Feature.Logic.FeatureObject;
import Feature.Logic.Features;
import Feature.CoreFeatures.Project;
import Generation.BuildTool.BuildGeneration;
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
    private String configPath;
    private FileReader fileReader;
    
    public Core(String configPath, String filesTreePath) {
        this.configPath = configPath;
        this.fileReader = new FileReader(configPath, filesTreePath);
    }

    public Core() {
        this.fileReader = new FileReader(CONFIG_PATH, FILES_TREE_PATH);
    }
    
    public Boolean start() {
        Project project = fileReader.readConfiguration();
        if (project == null) {
            System.out.println("ERROR IN START");
            System.exit(0);
        }
        Boolean isProjectFolderCreated = generateProjectDir(project.getProjectPath());
        CustomLogger.initPath(project.getProjectPath());
        CustomLogger.writeLog(LogType.INFO, "project created");
        if (!isProjectFolderCreated) {
            System.out.println("COULDN'T CREATE DIR");
            System.exit(0);
        }
        BuildGeneration buildGeneration = new GradleGeneration(project.getProjectPath());
        switch (project.getBuildType()) {
            case Gradle:
                buildGeneration = new GradleGeneration(project.getProjectPath());
                break;
            case Maven:
                buildGeneration = new MavenGeneration(project.getProjectPath());
                break;
        }
        buildGeneration.generate();
        
        ProjectGeneration projectGeneration = new ProjectGeneration(fileReader, project.getProjectPath());
        Boolean result = projectGeneration.generate();
    
        runAllFeatures();
        
        return result;
    }
    
    private void runAllFeatures() {
        Features<? extends FeatureObject> q = Features.getInstance();
        List<? extends FeatureObject> all = q.getFeatures();
        all.forEach(feature -> {
            feature.start();
        });
    }
    
    private Boolean generateProjectDir(String projectFolderPath) {
        try {
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx"); // TODO check on Windows!
            Files.createDirectories(Path.of(projectFolderPath), PosixFilePermissions.asFileAttribute(perms));
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
