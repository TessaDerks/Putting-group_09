package AI;

import main.Main;
import mazeAI.Shot;
import physics.*;

class Genetic {

    private static Vector2d start;
    private static Vector2d end;
    private static int popSize;
    private static Individual[] population;
    private static int generation = 0;
    private static double angle = 0.0;
    private static PuttingSimulator putting;
    public static boolean testCase = false;

    /**
     *
     * @param _popSize int, describes the size of the population, popSize > 0
     * @param _start Vector2d
     * @param _end Vector2d
     */
    public Genetic(int _popSize, Vector2d _start, Vector2d _end){
        start = _start;
        end = _end;
        popSize = _popSize;
        putting = SimulateMain.simulator;
    }

    public static void initializePopulation(){
        population = new Individual[popSize];
        for ( int i = 0; i < popSize; i++){
            population[i] = new Individual(start);
        }
    }

    /**
     * Calculates the fitness based on the Manhattan distance
     */
    public static void calcFitness(){ //
        double fitness;
        for ( int i = 0; i < popSize; i++){
            fitness = Math.sqrt(Math.pow(population[i].getPosition().get_x() - end.get_x(), 2) + Math.pow(population[i].getPosition().get_y() - end.get_y(), 2));
            population[i].setFitness(fitness);
        }
    }

    public static void sortPopulation(){
        Merge merge = new Merge();
        merge.sortList(population, 0, popSize-1);
    }

    /**
     *
     * @param maxSpeed double, 0 < maxSpeed <= 100, maxSpeed of the course
     * @return double random speed, 0 < return < maxSpeed
     */
    public static double generateSpeed(double maxSpeed){
        return Tools.advRound(Math.random() * maxSpeed, 2); // generate a random speed using the maxSpeed of the puttingsimulator as speed for a first shot
    }

    /**
     * generates the first shot
     */
    public static void takeFirstShot() { // take the first shot to gain an idea of with how much power the AI should shoot
        for (Individual individual : population) {
            putting.get_engine().resetPosition(start);
            double _speed = generateSpeed(putting.get_course().get_maximum_velocity());
            individual.setSpeed(_speed); // setting the speed for the certain individual
            Vector2d vel = Tools.velFromAngle(angle, _speed);
            putting.take_shot(vel, true);
            individual.setPosition(putting.get_ball_position());
        }
        generation++;
        calcFitness();
        sortPopulation();
    }

    /**
     *
     * @return boolean returns true for a ball on the left side of the hole and false for a ball on the right side
     */
    public static boolean leftOfHole(){
        boolean ret = false;
        putting.get_engine().resetPosition(start);
        putting.take_shot(Tools.velFromAngle(angle, (putting.get_course().get_maximum_velocity()/2)), true);

        // adjust the flag a little to the left and make a checker flag
        Vector2d checker = Tools.adjustFlagPosition(start, end);
        // calculate the distance to the flag and to the checker
        double disFlag = Math.sqrt(Math.pow(putting.get_ball_position().get_x() - end.get_x(), 2) + Math.pow(putting.get_ball_position().get_y() - end.get_y(), 2));
        double disCheck = Math.sqrt(Math.pow(putting.get_ball_position().get_x() - checker.get_x(), 2) + Math.pow(putting.get_ball_position().get_y() - checker.get_y(), 2));
        // if the flag is further away than the checker, the ball is on the left side of the flagd
        if(disFlag <= disCheck){
            ret = true;
        }
        return ret;
    }

    /**
     * finds the final angle, by adjusting the angle based on its previous position
     */
    public static void cocktailShaker(){ // shake the angle back and forth until the tolerance is small enough to create the right angle
        boolean leftRight = leftOfHole();
        double adjusting = 10.0;

        while(adjusting > 0.01){
            if (leftRight == leftOfHole()){ // if the ball lays on the same side as before, dont change the adjusting angle
                if(leftRight){ angle += adjusting;} // add up if its left
                else {angle -= adjusting;} //
            }
            else{
                leftRight = leftOfHole(); // set new value for leftright for the next check
                adjusting = adjusting/10; // divide adjusting by 10 to set the step size smaller
                if(leftRight){ angle += adjusting; }
                else{ angle -= adjusting; }
            }
            /*
            if(angle >= 360) {
                angle = 0.0;
                counter++;
            }
            if(angle < 0) {
                angle = 360 + angle;
            }
            */

            //if(counter > 3){
            //    calculateAngle();
            //    adjusting = 0.000001;
            //}
        }
    }

    /**
     * Genetic algorithm to find the final speed, adds the shot to the final list of shots
     */
    public static void finishGame() { // finish the game after you have taken the first shot
        double clearance = 5;
        boolean win = putting.calcWin(population[0].getPosition(),end, clearance);
        int popSizeNew = popSize/2;
        while(!win) {
            double speed = population[0].getSpeed();
                for (int i = 0; i < popSizeNew; i++) { // split population in half, one with a higher speed
                    putting.get_engine().resetPosition(start); // setting the position to the start position
                    double _speed = speed; // set standard speed to best scoring individual from previous shot
                    _speed +=Tools.advRound((Math.random() * 0.5), 2); // adjust speed
                    if (_speed > putting.get_course().get_maximum_velocity()) break; // cancel the shot if the speed goes above the maximum speed
                    population[i].setSpeed(_speed);
                    Vector2d botVel = Tools.velFromAngle(angle, _speed);
                    putting.take_shot(botVel, true);
                    population[i].setPosition(putting.get_ball_position());
                }
                for (int j = popSizeNew; j < popSize; j++) {
                    putting.get_engine().resetPosition(start);
                    double _speed = speed;
                    _speed -= Tools.advRound((Math.random()*0.5), 2);// one with lower speed
                    if (_speed > putting.get_course().get_maximum_velocity()) break;
                    population[j].setSpeed(_speed);
                    Vector2d botVel = Tools.velFromAngle(angle, _speed);
                    putting.take_shot(botVel, true);
                    population[j].setPosition(putting.get_ball_position());
                }
                calcFitness();
                sortPopulation();
                generation++;

                win = putting.calcWin(population[0].getPosition(),end, clearance);
            }
            TestAI.addBotShots(new Shot(angle,population[0].getSpeed()));
            System.out.println("Congrats! Bot made a hole in one!");
            if(!testCase){
                putting.get_engine().resetPosition(SimulateMain.getStart());
                putting.take_shot( Tools.velFromAngle(angle, population[0].getSpeed()), false);
                Main.takingShot = true;
                Main.openNewWindow = true;
            }
            System.out.println("Winning velocity: angle "+ angle + " & speed " + population[0].getSpeed());
    }

    // getters and setters
    public static Individual[] getPopulation() {
        return population;
    }

    public static int getGeneration() {
        return generation;
    }



}
