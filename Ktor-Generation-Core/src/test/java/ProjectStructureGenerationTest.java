import Generation.Project.Node;
import Generation.Project.Tree;
import Handler.Core;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import utils.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static Constant.Constant.CONFIG_PATH;
import static Constant.Constant.FILES_TREE_PATH;

public class ProjectStructureGenerationTest {
    
    @BeforeEach
    public void removeUserFolder() {
        try {
            FileUtils.deleteDirectory(new File("user"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    @Test
    public void simpleProject() {
        String path = getClass().getClassLoader().getResource("projectSimple.tr").getPath();
        Core core = new Core(CONFIG_PATH, path);
        core.start();
        
        List<String> namesInFile = Reader.readFilesNameInFile(path);
        List<String> namesGeneratedFiles = Reader.readNamesInGeneratedFile();
        
        Assertions.assertTrue(namesGeneratedFiles.containsAll(namesInFile));
    }
    
    @Test
    public void projectWithError() {
        String path = getClass().getClassLoader().getResource("projectError.tr").getPath();
        Core core = new Core(CONFIG_PATH, path);
        Boolean result = core.start();
        
        List<String> namesInFile = Reader.readFilesNameInFile(path);
        List<String> namesGeneratedFiles = Reader.readNamesInGeneratedFile();
        
        /*for (String name: namesInFile) {
            Assertions.assertFalse(namesGeneratedFiles.contains(name));
        }*/
        Assertions.assertFalse(result);
    }
}
