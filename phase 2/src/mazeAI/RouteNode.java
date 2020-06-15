package mazeAI;

import org.jetbrains.annotations.NotNull;

class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    RouteNode(T current){
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(@NotNull RouteNode other) {
        if (this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if (this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
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
