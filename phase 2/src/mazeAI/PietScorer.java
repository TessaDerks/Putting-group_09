package mazeAI;

public class PietScorer<T> implements Scorer<CheckPoint>{
    @Override
    public double computeCost(CheckPoint from, CheckPoint to) {
        double a = Math.abs(from.getX() - to.getX());
        double b = Math.abs(from.getY() - to.getY());
        double c = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
        return c;
    }
}
