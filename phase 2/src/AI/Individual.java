package AI;

import physics.*;

public class Individual {
    private Double fitness;
    private Vector2d position;
    private Double speed;

    /**
     *
     * @param pos Vector2d, set starting position for certain individual
     */
    Individual(Vector2d pos){
        fitness = 0.0;
        position = pos;
        speed = 0.0;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Double getSpeed() {return speed;}

    public void setSpeed(Double speed) { this.speed = speed; }
}