package mazeAI;

public interface Scorer<T extends GraphNode> {
    /**
     *
     * @param from Starting checkpoint
     * @param to End checkpoint
     * @return double, score
     */
    double computeCost(T from, T to);
}
