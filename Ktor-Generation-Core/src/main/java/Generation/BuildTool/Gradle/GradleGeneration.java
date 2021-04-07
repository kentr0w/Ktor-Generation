package Generation.BuildTool.Gradle;

import Copy.Replication;
import Generation.BuildTool.BuildGeneration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static Constant.Constant.GRADLE_BUILD_PATH;

public class GradleGeneration extends BuildGeneration {
    
    public GradleGeneration(String projectFolder) {
        super(projectFolder);
    }

    @Override
    protected Boolean initInfo() {
        return true;
    }
}
