package Generation.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Node {
    private String name;
    private Integer level;
    private Boolean isDir;
    private Node parent;
    private List<Node> children;
    
    public Node(String node) {
        char [] array = node.toCharArray();
        int count = 0;
        this.level = count;
        this.children = new ArrayList<>();
        while (array[count] == ' ') {
            count++;
            this.level = count;
        }
        this.level /= 4;
        String name = "";
        while (array[count] != ' ') {
            name += array[count];
            count++;
        }
        this.name = name;
        String type = "";
        count++;
        while (count < array.length) {
            type += array[count];
            count++;
        }
        this.isDir = type.equals("dir");
    }
    
    public void addChild(Node node) {
        this.children.add(node);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Boolean getDir() {
        return isDir;
    }
    
    public void setDir(Boolean dir) {
        isDir = dir;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    public void setChildren(List<Node> children) {
        this.children = children;
    }
}

