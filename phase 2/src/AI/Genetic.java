package AI;

import main.Main;
import mazeAI.Shot;
import physics.*;

class Genetic {

    private static Vector2d start;
    private static Vector2d position;
    private static Vector2d end;
    private static int popSize;
    private static Individual[] population;
    private static int generation = 0;
    private static Double speed;
    private static Double angle = 0.0;
    private static Function2d function;
    private static PuttingSimulator putting;
    public static boolean testCase = false;

    
    public Genetic(int _popSize){
        start = SimulateMain.getStart();
        position = start;
        end = SimulateMain.getFlag();
        popSize = _popSize;
        function = SimulateMain.getFunction();
        putting = SimulateMain.simulator;
    }

    public Genetic(int _popSize, Vector2d _start, Vector2d _end){
        start = _start;
        position = start;
        end = _end;
        System.out.println("Start of Gen!"+start.toString()+end.toString());
        popSize = _popSize;
        function = SimulateMain.getFunction();
        putting = SimulateMain.simulator;
    }

    public static void initializePopulation(){
        population = new Individual[popSize];
        for ( int i = 0; i < popSize; i++){
            population[i] = new Individual(start);
        }
    }

    public static void calcFitness(){ // calculating the distance between the end position and the flag, lowest fitness is the best score
        Double fitness;
        for ( int i = 0; i < popSize; i++){
            fitness = Math.sqrt(Math.pow(population[i].getPosition().get_x() - end.get_x(), 2) + Math.pow(population[i].getPosition().get_y() - end.get_y(), 2));
            population[i].setFitness(fitness);
        }
    }

    public static void sortPopulation(){
        Merge merge = new Merge();
        merge.sortList(population, 0, popSize-1);
    }

    public static double generateSpeed(Double maxSpeed){
        Double _speed = Tools.advRound(Math.random() * maxSpeed, 2);
        return _speed; // generate a random speed using the maxSpeed of the puttingsimulator as speed for a first shot
    }

    public static void takeFirstShot(){ // take the first shot to gain an idea of with how much power the AI should shoot
        for ( int i = 0; i < population.length; i++){
            putting.get_engine().resetPosition(start);
            Double _speed = generateSpeed(putting.get_course().get_maximum_velocity());
            population[i].setSpeed(_speed); // setting the speed for the certain individual
            Vector2d vel = Tools.velFromAngle(angle, _speed);
            putting.take_shot(vel, true);
            population[i].setPosition(putting.get_ball_position());
        }
        generation++;
        calcFitness();
        sortPopulation();
    }

    public static void calculateAngle(){ // calculate the angle
        angle = Math.toDegrees(Math.atan(((Math.abs(end.get_x()))/(Math.abs(end.get_y())))));
        if (end.get_y() < 0) {
            if (end.get_x() > 0){ angle += 90;;}
            else{ angle += 180 ;}
        }
        else if ((end.get_y() > 0) &&( end.get_x() < 0) ) {angle +=270;}
    }

    public static boolean leftOfHole(){
        boolean ret = false;
        putting.get_engine().resetPosition(start);
        putting.take_shot(Tools.velFromAngle(angle, (putting.get_course().get_maximum_velocity()/2)), true);

        // adjust the flag a little to the left and make a checker flag
        Vector2d checker = Tools.adjustFlagPosition(start, end);
        // calculate the distance to the flag and to the checker
        Double disFlag = Math.sqrt(Math.pow(putting.get_ball_position().get_x() - end.get_x(), 2) + Math.pow(putting.get_ball_position().get_y() - end.get_y(), 2));
        Double disCheck = Math.sqrt(Math.pow(putting.get_ball_position().get_x() - checker.get_x(), 2) + Math.pow(putting.get_ball_position().get_y() - checker.get_y(), 2));
        //System.out.println(end.toString() + " " + disFlag);
        //System.out.println(checker.toString() + " " + disCheck);
        // if the flag is further away than the checker, the ball is on the left side of the flagd
        if(disFlag <= disCheck){
            ret = true;
        }
        return ret;
    }

    public static void cocktailShaker(){ // shake the angle back and forth until the tolerance is small enough to create the right angle
        boolean leftRight = leftOfHole();
        Double adjusting = 10.0;
        Double counter = 0.0;

        while(adjusting > 0.01){
            if (leftRight == leftOfHole()){ // if the ball lays on the same side as before, dont change the adjusting angle
                if(leftRight == true){ angle += adjusting;} // add up if its left
                else {angle -= adjusting;} //
                System.out.println(angle);
            }
            else{
                leftRight = leftOfHole(); // set new value for leftright for the next check
                adjusting = adjusting/10; // divide adjusting by 10 to set the step size smaller
                //System.out.println(adjusting);
                if(leftRight == true){ angle += adjusting; }
                else{ angle -= adjusting; }
                System.out.println(angle);
            }
            if(angle >= 360) {
                angle = 0.0;
                counter++;
            }
            if(angle < 0) {
                angle = 360 + angle;
            }
            //if(counter > 3){
            //    calculateAngle();
            //    adjusting = 0.000001;
            //}
        }
    }

    public static void finishGame() { // finish the game after you have taken the first shot
        boolean win = putting.calcWin(population[0].getPosition(),end);
        int popSizeNew = (int) Tools.advRound(popSize/2, 0);
        while(!win) {
                speed = population[0].getSpeed();
                for (int i = 0; i < popSizeNew; i++) { // split population in half, one with a higher speed
                    putting.get_engine().resetPosition(start); // setting the position to the start position
                    Double _speed = speed; // set standard speed to best scoring individual from previous shot
                    _speed +=Tools.advRound((Math.random() * 0.5), 2); // adjust speed
                    if (_speed > putting.get_course().get_maximum_velocity()) break; // cancel the shot if the speed goes above the maximum speed
                    population[i].setSpeed(_speed);
                    Vector2d botVel = Tools.velFromAngle(angle, _speed);
                    putting.take_shot(botVel, true);
                    population[i].setPosition(putting.get_ball_position());
                    //System.out.println(_speed);
                }
                for (int j = popSizeNew; j < popSize; j++) {
                    putting.get_engine().resetPosition(start);
                    Double _speed = speed;
                    _speed -= Tools.advRound((Math.random()*0.5), 2);// one with lower speed
                    if (_speed > putting.get_course().get_maximum_velocity()) break;
                    population[j].setSpeed(_speed);
                    Vector2d botVel = Tools.velFromAngle(angle, _speed);
                    putting.take_shot(botVel, true);
                    population[j].setPosition(putting.get_ball_position());
                    //System.out.println(_speed);
                }
                calcFitness();
                sortPopulation();
                generation++;

                win = putting.calcWin(population[0].getPosition(),end);
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

    public static Double CalculateAmountShots(){ // calculate if the bot can reach the destination
        putting.get_engine().resetPosition(start);
        putting.take_shot(Tools.velFromAngle(90, putting.get_course().get_maximum_velocity()), true);

        Double shotDis = putting.get_ball_position().get_x() - putting.get_course().get_start_position().get_x(); // by shooting the ball at 90 degrees, the x value is the maximum distance it can shoot
        Double disFlag = Math.sqrt(Math.pow((end.get_x()-start.get_x()-putting.get_course().get_hole_tolerance()), 2) + Math.pow((end.get_y()-start.get_y()-putting.get_course().get_hole_tolerance()), 2));
        Double shots = (disFlag/shotDis); // calculate the amount of shots needed to score the hole in one based on distance
        return shots; // if the value is higher than 1, it can not shoot the hole in one
    }

    // getters and setters
    public static Vector2d getStart() {
        return start;
    }

    public static void setStart(Vector2d start) {
        Genetic.start = start;
    }

    public static Vector2d getPosition() {
        return position;
    }

    public static void setPosition(Vector2d position) {
        Genetic.position = position;
    }

    public static Vector2d getEnd() {
        return end;
    }

    public static void setEnd(Vector2d end) {
        Genetic.end = end;
    }

    public static int getPopSize() {
        return popSize;
    }

    public static void setPopSize(int popSize) {
        Genetic.popSize = popSize;
    }

    public static Individual[] getPopulation() {
        return population;
    }

    public static void setPopulation(Individual[] population) {
        Genetic.population = population;
    }

    public static Double getSpeed() {
        return speed;
    }

    public static void setSpeed(Double speed) {
        Genetic.speed = speed;
    }

    public static Double getAngle() {
        return angle;
    }

    public static void setAngle(Double angle) {
        Genetic.angle = angle;
    }

    public static Function2d getFunction() {
        return function;
    }

    public static void setFunction(Function2d function) {
        Genetic.function = function;
    }

    public static int getGeneration() {
        return generation;
    }



}
