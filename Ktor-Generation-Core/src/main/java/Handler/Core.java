package Handler;

import Feature.Logic.FeatureObject;
import Feature.Features;
import Feature.Project;
import Generation.BuildTool.Gradle.GradleGeneration;
import Reader.ConfigReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

public class Core {
    
    private String configPath;
    private ConfigReader configReader;
    
    public Core(String configPath) {
        this.configPath = configPath;
        this.configReader = new ConfigReader(this.configPath);
    }
    
    public void start() {
        Project project = configReader.read();
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
        for(FeatureObject w: all) {
            System.out.println(w);
        }
        GradleGeneration gradleGeneration = new GradleGeneration(project.getId());
        gradleGeneration.generate();
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
