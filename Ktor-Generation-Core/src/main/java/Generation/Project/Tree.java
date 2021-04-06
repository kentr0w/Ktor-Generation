package Generation.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Tree {
    private List<Node> nodes;
    private Node root;
    
    public Node getRoot() {
        return root;
    }
    
    public void setRoot(Node root) {
        this.root = root;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public Tree() {
        nodes = new ArrayList<>();
    }
    
    public void addNode(Node newNode) {
        if (this.root == null) {
            this.root = newNode;
        }
        this.nodes.add(newNode);
        List<Node> copy = new ArrayList<>(nodes);
        Collections.reverse(copy);
        copy.stream().filter(node -> {
            return node.getLevel().equals(newNode.getLevel() - 1);
        }).findFirst().ifPresent(node -> {
            newNode.setParent(node);
            node.addChild(newNode);
        });
    }
}

