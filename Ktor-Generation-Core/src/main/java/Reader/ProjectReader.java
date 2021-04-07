package Reader;

import Copy.Replication;
import Generation.Project.Node;
import Generation.Project.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProjectReader {
    
    private Tree tree;
    private List<Integer> levels;
    private String projectFilePath;
    
    public ProjectReader(String projectFilePath) {
        this.projectFilePath = projectFilePath;
        this.tree = new Tree();
        this.levels = new ArrayList<>();
    }
    
    public Tree read() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.projectFilePath));
            String line = reader.readLine();
            while (line != null) {
                Node node = new Node(line);
                this.tree.addNode(node);
                this.levels.add(node.getLevel());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (isFileCorrect())
            return this.tree;
        else
            return null;
    }
    
    private Boolean isFileCorrect() {
        Boolean result = true;
        for (int i = 1; i < this.levels.size(); i++) {
            result = (levels.get(i) - 1 == levels.get(i-1) || levels.get(i) < levels.get(i-1)) && result;
        }
        return result;
    }
}
