package mazeAI;

import physics.Function;
import physics.SimulateMain;
import physics.Vector2d;

import java.util.*;

public class RouteFinder<T extends GraphNode> {
    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;

    public RouteFinder(Graph<T> _graph){
        graph = _graph;
        nextNodeScorer = new PietScorer();
        targetScorer = new PietScorer();
    }

    public List<T> findRoute(T from, T to){
        Queue<RouteNode> openSet = new PriorityQueue<>(); // Nodes to be considered for next step
        Map<T, RouteNode<T>> allNodes = new HashMap<>(); // Maps nodes to the step that has been taken from those nodes

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to)); // First node on route
        openSet.add(start); // Add a node to consider for next step
        allNodes.put(from, start); // Store a node we have discovered

        while (!openSet.isEmpty()) {
            RouteNode<T> next = openSet.poll();

            // When the end point is reached
            if (next.getCurrent().equals(to)) {
                List<T> route = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);
                return route;
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                if(SimulateMain.simulator.get_engine().get_h().evaluate(new Vector2d(nextNode.getCurrent().getX(),nextNode.getCurrent().getY()))>=0||!SimulateMain.simulator.get_course().nodeOnTree(new Vector2d(nextNode.getCurrent().getX(),nextNode.getCurrent().getY()))){
                    allNodes.put(connection, nextNode);

                    double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                    if (newScore < nextNode.getRouteScore()) {
                        nextNode.setPrevious(next.getCurrent());
                        nextNode.setRouteScore(newScore);
                        nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                        openSet.add(nextNode);
                    }
                }
            });

        }

        throw new IllegalStateException("No route found");
    }



}
