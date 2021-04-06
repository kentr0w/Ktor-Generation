package Handler;

import Feature.Logic.FeatureObject;
import Feature.Logic.Features;
import Feature.CoreFeatures.Project;
import Generation.BuildTool.BuildGeneration;
import Generation.BuildTool.Gradle.GradleGeneration;
import Generation.BuildTool.Maven.MavenGeneration;
import Generation.Project.ProjectGeneration;
import Generation.Project.Tree;
import Reader.ConfigReader;
import Reader.FileReader;

import java.io.File;
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
    
    private String configPath;
    private FileReader fileReader;
    
    public Core(String configPath, String filesTreePath) {
        this.configPath = configPath;
        this.fileReader = new FileReader(configPath, filesTreePath);
    }

    public Core() {
        this.fileReader = new FileReader(CONFIG_PATH, FILES_TREE_PATH);
    }
    
    public void start() {
        Project project = fileReader.readConfig();
        if (project == null) {
            System.out.println("ERROR IN START");
            System.exit(0);
        }
        if (!generateProjectDir(project.getId())) {
            System.out.println("COULDN'T CREATE DIR");
            System.exit(0);
        }
        Features<? extends FeatureObject> q = Features.getInstance();
        List<? extends FeatureObject> all = q.getFeatures();
        BuildGeneration buildGeneration = new GradleGeneration(project.getId());
        switch (project.getGlobal().getBuildType()) {
            case Gradle:
                buildGeneration = new GradleGeneration(project.getId());
                break;
            case Maven:
                buildGeneration = new MavenGeneration(project.getId());
                break;
        }
        buildGeneration.generate();
        
        ProjectGeneration projectGeneration = new ProjectGeneration(fileReader, project.getId());
        projectGeneration.generate();
    }
    
    private boolean generateProjectDir(String projectId) {
        try {
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx"); // TODO check on Windows!
            Files.createDirectories(Path.of(projectId), PosixFilePermissions.asFileAttribute(perms));
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
