package mazeAI;

import org.jetbrains.annotations.NotNull;

public class PietScorer<T> implements Scorer<CheckPoint>{
    @Override
    public double computeCost(@NotNull CheckPoint from, @NotNull CheckPoint to) {
        double a = Math.abs(from.getX() - to.getX());
        double b = Math.abs(from.getY() - to.getY());
        double c = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
        return c;
    }
}
