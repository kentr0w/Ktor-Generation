package Generation.Project;

import Copy.CustomLogger;
import Copy.LogType;
import Copy.Replication;
import Reader.FileReader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectGeneration {
    
    private Tree tree;
    private String projectFolder;
    
    public ProjectGeneration(FileReader fileReader, String projectFolder) {
        CustomLogger.writeLog(LogType.INFO, "Starting to generate project structure");
        this.tree = fileReader.readProject();
        this.projectFolder = projectFolder;
    }
    
    public Boolean generate() {
        if (this.tree == null) {
            CustomLogger.writeLog(LogType.INFO, "Project structure wasn't presented");
            return false;
        }
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
        return result[0];
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
}
