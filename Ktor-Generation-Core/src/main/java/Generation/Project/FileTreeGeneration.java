package Generation.Project;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Generation.BuildTool.Gradle.GradleGeneration;
import Reader.FileReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Constant.Constant.*;

public class FileTreeGeneration {
    
    private Tree tree;
    private String projectFolder;
    
    public FileTreeGeneration(Tree tree, String projectFolder) {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate project structure");
        this.tree = tree;
        this.projectFolder = projectFolder;
    }
    
    public Boolean generate() {
        if (this.tree == null) {
            CustomLogger.writeLog(LogType.INFO, "Project structure wasn't presented");
            return false;
        }
        Boolean isSourcesGenerated = generateResources() & generateSRC();
        Node root = this.tree.getRoot();
        root.setName(projectFolder + File.separator + root.getName());
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Boolean[] result = { createFile(root, root) };
        if (result[0]) {
            CustomLogger.writeLog(LogType.INFO, root.getName() + " file was created");
        } else {
            CustomLogger.writeLog(LogType.ERROR, root.getName() + " file wasn't created");
        }
        while (!queue.isEmpty()) {
            Node peek = queue.remove();
            peek.getChildren().forEach(node -> {
                queue.add(node);
                Boolean isCreated = createFile(root, node);
                result[0] = result[0] || isCreated;
                if (result[0]) {
                    CustomLogger.writeLog(LogType.INFO, node.getName() + " file was created");
                } else {
                    CustomLogger.writeLog(LogType.ERROR, node.getName() + " file wasn't created");
                }
            });
        }
        return result[0] && isSourcesGenerated;
    }
    
    private Boolean createFile(Node root, Node node) {
        String realPath = getPath(root, node);
        return Replication.generateSrc(realPath, node.getDir());
    }
    
    private String getPath(Node root, Node node) {
        List<String> pathPart = new ArrayList<>();
        pathPart.add(node.getName());
        Node parent = node.getParent();
        if (parent != null) {
            while (parent != root) {
                pathPart.add(parent.getName());
                parent = parent.getParent();
            }
            pathPart.add(root.getName());
        }
        Collections.reverse(pathPart);
        return pathPart.stream().collect(Collectors.joining(File.separator));
    }
    
    public Boolean insertPackageInKtFiles(String group) {
        String startPath = this.tree.getRoot().getName();
        try{
            Files
                .walk(Path.of(startPath))
                .forEach(path -> {
                    File file = path.toFile();
                    if (isPossibleInsertPackage(file)) {
                        insertPackage(file, group);
                    }
                });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return true;
    }
    
    private Boolean isPossibleInsertPackage(File file) {
        return file.exists() && file.isFile() && !file.getName().equals("Application.kt");
    }
    
    private Boolean insertPackage(File file, String group) {
        String realPackage = "package " + group + calculatePackage(file);
        try {
            FileUtils.write(file, realPackage, "UTF-8");
            CustomLogger.writeLog(LogType.INFO, "Wrote package in " + file.getPath() + " file");
            return true;
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.INFO, "Couldn't write package in " + file.getPath() + " file");
            return false;
        }
        
    }
    
    private String calculatePackage(File file) {
        List<String> pathToFile = Arrays.asList(file.getPath().split(File.separator));
        pathToFile = pathToFile
                .stream()
                .dropWhile(it -> !it.equals("src"))
                .collect(Collectors.toList());
        pathToFile.remove(0);
        if (pathToFile.size() > 0)
            pathToFile.remove(pathToFile.size() - 1);
        String realPackage = pathToFile.stream().collect(Collectors.joining(""));
        if (!realPackage.equals(""))
            realPackage = "." + realPackage;
        realPackage += StringUtils.repeat(System.lineSeparator(), 2); // two line to correct input import
        return realPackage;
    }
    
    private Boolean generateResources() {
        CustomLogger.writeLog(LogType.INFO, "Starting copied resources folder");
        return generateSourceFiles("resources", Stream.of("application.conf.tmp", "logback.xml.tmp"), RESOURCES_BUILD);
    }
    
    
    private Boolean generateSRC() {
        CustomLogger.writeLog(LogType.INFO, "Starting copied src folder");
        return generateSourceFiles("src", Stream.of("Application.kt.tmp"), SRC_BUILD);
    }
    
    private Boolean generateSourceFiles(String folder, Stream<String> stream, String templates) {
        File file = new File(projectFolder + File.separator + folder);
        if (!file.exists())
            file.mkdirs();
        final Boolean[] isCopySuccessful = {true};
        stream.forEach(it -> {
            InputStream resourceAsStream = FileTreeGeneration.class.getClassLoader().getResourceAsStream(templates + it);
            isCopySuccessful[0] = Replication.copyInputStream(resourceAsStream, file.getPath(), it) && isCopySuccessful[0];
            try {
                resourceAsStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
        });
        if (isCopySuccessful[0]) {
            CustomLogger.writeLog(LogType.INFO, folder + " files was created successfully ");
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Error with " + folder + " files creation");
        }
        return isCopySuccessful[0];
    }
}
