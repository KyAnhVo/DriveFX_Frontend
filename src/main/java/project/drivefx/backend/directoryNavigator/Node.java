package project.drivefx.backend.directoryNavigator;

import java.util.ArrayList;
import java.util.List;

class Node {
    String name;
    final List<Node> children;

    public Node(String name) {
        this.name = name;
        children = new ArrayList<Node>();
    }

    public Node getChild(String name) {
        for (Node child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public String toString() {
        return name;
    }
}
