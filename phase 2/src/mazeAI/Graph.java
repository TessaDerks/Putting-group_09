package mazeAI;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stijn Hennissen
 */
public class Graph<T extends GraphNode> {
    private final Set<T> nodes; // Set of all nodes in our Graph
    private final Map<String, Set<String>> connections; // Mapping of node id's to all id's connected to this node

    /**
     *
     * @param _nodes Set, set of all nodes in the graph
     * @param _connections Map, map of all connections between the nodes
     */
    public Graph(Set<T> _nodes, Map<String, Set<String>> _connections){
        nodes = _nodes;
        connections = _connections;
    }

    // Get the node with a specific id

    /**
     *
     * @param id String, id of the node, consisting of its checkpoint
     * @return Node matching the Id
     * @throws IllegalArgumentException whenever a certain id is not found
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
     * @param node String, id of node
     * @return Set of connections
     */
    public Set<T> getConnections(@NotNull T node){
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
