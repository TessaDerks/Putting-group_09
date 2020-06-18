package mazeAI;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends GraphNode> {
    private final Set<T> nodes; // Set of all nodes in our Graph
    private final Map<String, Set<String>> connections; // Mapping of node id's to all id's connected to this node

    /**
     *
     * @param _nodes
     * @param _connections
     */
    public Graph(Set<T> _nodes, Map<String, Set<String>> _connections){
        nodes = _nodes;
        connections = _connections;
    }

    // Get the node with a specific id

    /**
     *
     * @param id
     * @return
     */
    public T getNode(String id){
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID " + id));
    }

    // Get all the nodes connected to a specific node
    /**
     *
     * @param node
     * @return
     */
    public Set<T> getConnections(@NotNull T node){
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
