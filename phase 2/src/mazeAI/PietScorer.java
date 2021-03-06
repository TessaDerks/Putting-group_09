package mazeAI;

import org.jetbrains.annotations.NotNull;

/**
 * @author Stijn Hennissen
 */
public class PietScorer<T> implements Scorer<CheckPoint>{
    /**
     * Computes the score in between two checkpoints
     * @param from Checkpoint, starting checkpoint
     * @param to Checkpoint, end checkpoint
     * @return double, Manhattan distance between the checkpoints
     */
    @Override
    public double computeCost(@NotNull CheckPoint from, @NotNull CheckPoint to) {
        double a = Math.abs(from.getX() - to.getX());
        double b = Math.abs(from.getY() - to.getY());
        return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
    }
}
