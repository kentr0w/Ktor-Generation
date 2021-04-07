package Generation.Project;

import Copy.Replication;
import Reader.FileReader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectGeneration {
    
    private Tree tree;
    private String projectFolder;
    
    public ProjectGeneration(FileReader fileReader, String projectFolder) {
        this.tree = fileReader.readProject();
        this.projectFolder = projectFolder;
    }
    
    public Boolean generate() {
        if (this.tree == null)
            return false;
        Node root = this.tree.getRoot();
        root.setName(projectFolder + File.separator + root.getName());
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Boolean[] result = { createFile(root, root) };
        while (!queue.isEmpty()) {
            Node peek = queue.remove();
            peek.getChildren().forEach(node -> {
                queue.add(node);
                Boolean isCreated = createFile(root, node);
                result[0] = result[0] || isCreated;
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
