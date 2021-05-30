package Reader;

import Copy.CustomLogger;
import Copy.LogType;
import Generation.Project.Node;
import Generation.Project.Tree;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileTreeReader {
    
    private Tree tree;
    private List<Integer> levels;
    private InputStream fileTreeConfigStream;
    
    public FileTreeReader(InputStream fileTreeConfigStream) {
        this.fileTreeConfigStream = fileTreeConfigStream;
        this.tree = new Tree();
        this.levels = new ArrayList<>();
    }
    
    public Tree read() {
        CustomLogger.writeLog(LogType.INFO, "Starting to read project structure");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(this.fileTreeConfigStream));
            String line = reader.readLine();
            while (line != null) {
                Node node = new Node(line);
                this.tree.addNode(node);
                this.levels.add(node.getLevel());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            CustomLogger.writeLog(LogType.ERROR, e.getMessage());
            return null;
        }
        if (isFileCorrect()) {
            CustomLogger.writeLog(LogType.INFO, "File was read successfully");
            return this.tree;
        } else {
            CustomLogger.writeLog(LogType.ERROR, "File with project file description contains error");
            return null;
        }
    }
    
    private Boolean isFileCorrect() {
        Boolean result = true;
        for (int i = 1; i < this.levels.size(); i++) {
            result = (levels.get(i) - 1 == levels.get(i-1) || levels.get(i) <= levels.get(i-1)) && result;
        }
        result = this.levels.stream().filter(it -> it.equals(0)).count() == 1L && result;
        Boolean isApplicationFile = this.tree.getRoot()
                .getChildren()
                .stream()
                .filter(it -> !it.getDir())
                .map ( it -> {
                        System.out.println(it.getName().replaceAll("\\s+",""));
                        return it.getName().replaceAll("\\s+","");
                    }
                )
                .collect(Collectors.toList())
                .contains("Application.kt");
        result = this.tree.getRoot().getName().equals("src") && result && isApplicationFile;
        return result;
    }
}
