package mazeAI;

import org.jetbrains.annotations.NotNull;

class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    /**
     * Constructor for first node
     * @param current T, starting node
     */
    RouteNode(T current){
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     *  Constructor for all nodes but the first
     * @param current
     * @param previous
     * @param routeScore
     * @param estimatedScore
     */
    RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(@NotNull RouteNode other) {
        return Double.compare(this.estimatedScore, other.estimatedScore);
    }

    //<editor-fold desc="Getters & Setters">
    public T getCurrent(){
        return current;
    }

    public T getPrevious(){
        return previous;
    }

    public double getRouteScore() {
        return routeScore;
    }

    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
    }

    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }
    //</editor-fold>
}
