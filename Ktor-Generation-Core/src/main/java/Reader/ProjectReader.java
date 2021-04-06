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

public class ProjectReader {
    
    private Tree tree;
    private String projectFilePath;
    
    public ProjectReader(String projectFilePath) {
        this.projectFilePath = projectFilePath;
        this.tree = new Tree();
    }
    
    public Tree read() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.projectFilePath));
            String line = reader.readLine();
            while (line != null) {
                Node node = new Node(line);
                this.tree.addNode(node);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return this.tree;
    }
}
