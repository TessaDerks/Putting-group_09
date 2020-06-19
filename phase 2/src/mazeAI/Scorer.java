package mazeAI;

public interface Scorer<T extends GraphNode> {
    /**
     *
     * @param from
     * @param to
     * @return
     */
    double computeCost(T from, T to);
}
