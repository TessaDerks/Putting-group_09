package AI;

import javafx.util.Pair;
import physics.*;


import java.util.ArrayList;
import java.util.List;

class RecursiveAlgorithm {

    private static Vector2d start;
    private Vector2d landing;
    private static Vector2d end;
    private Double speed;
    private Double angle = 0.0;
    private Function2d function;
    private PuttingSimulator putting = SimulateMain.simulator;


    public RecursiveAlgorithm(Vector2d _start, Vector2d _finish, Function2d _function){
        start = _start;
        end = _finish;
        function = _function;
    }

    //constructor for graphics use of AI
    /*public RecursiveAlgorithm(){
        start = SimulateMain.getStart();
        position = start;
        end = SimulateMain.getFlag();
        function = SimulateMain.getFunction();
        putting = SimulateMain.simulator;
    }
    */


    //make sure the speed will be generated randomly
    //the lower the score, the less the speed changes
    private double adjust_speed(double maxSpeed) {
        maxSpeed = 10; //how to import the maxspeed value on the game (maybe retrieve it using the constructor?)
        double new_speed;
        double speed = Tools.advRound(Math.random() * maxSpeed, 2); //the 2 is the amount of decimals that the speed is rounded to
        Double curr_score = score();
        double totaldistance = Math.sqrt(Math.pow(end.get_x() - start.get_x(), 2) + Math.pow(end.get_y() - start.get_y(), 2));
        double normalized = curr_score/totaldistance;
        double percentage = normalized * 10; //the number is off how big them angle will change when 100

        double speed_change = Math.random() * percentage;
        double randomizer = Math.random();
        if(randomizer < 0.5) {
            new_speed = speed - speed_change;
        }
        else {
            new_speed = speed + speed_change;
        }

        return new_speed;
    }
    // in case the initial angle doesnt make the ball end up in the hole (the initial angle is the angle calculated in the finishGame method)
    // let it adjust the angle
    //the lower the score, the less the angle changes
    private double adjust_angle(double initial_angle) {
        initial_angle = calculateAngle(); //import it from the finishGame method
        double new_angle;

        Double curr_score = score();
        double totaldistance = Math.sqrt(Math.pow(end.get_x() - start.get_x(), 2) + Math.pow(end.get_y() - start.get_y(), 2));
        double normalized = curr_score/totaldistance;
        double percentage = normalized * 10; //the number is off how big them angle will change when 100
        double angle_change = Math.random() * percentage;

        double randomizer = Math.random();
        if(randomizer < 0.5) {
            new_angle = initial_angle - angle_change;
        }
        else {
            new_angle = initial_angle + angle_change;
        }

        return new_angle;
    }

    public double calculateAngle(){ // calculate the angle, checken of dit goed is
        angle = (double) Math.toDegrees(Math.atan2(end.get_y() - start.get_y(),end.get_x() - start.get_x()));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    // the closer to the hole, the lower the score
    // if there is water score is higher
    // if there is rocks, score is higher
    // lower score == better
    // score is representeed by the distance between the ball and the hole
    private double score(){        //parameter physics.Vector2d ending removed

        double _score = Math.sqrt(Math.pow(putting.get_ball_position().get_x() - end.get_x(), 2) + Math.pow(putting.get_ball_position().get_y() - end.get_y(), 2));

        return _score;
    }

    private boolean inHole() {
        if(score() <= 0.2) {
            return true;
        }
        else{
            return false;
        }
    }


    // Based on previous scored speeds/ angle we will alter the speed/ angle by small margins to
    // increase the score(so get it closer to 0), if it doesn't increase after 10 alterations, disgard and go back 1 step.
    // while loop entry (as long as there are no better children, we increase volitility of the random alteration)
    // for loop for 10 shots, randomly changing the speed/ angle after each shot, store the calculated score
    // every shot check if you reached the hole (ake score == 0)
    // after loop:::
    // pick lowest score from shots, if lowest > begin score -> increase volitility of alterations on speed/ angle
    // while loop exit

    // recursive call
    // if lowest score < begin score, recursive call to getpath with new values
    // return speed/ angle when score == 0
    private Vector2d getpath(double speed_heuristic, double angle_heuristic, Double score, int depth) {

        double new_score;

        if (depth > 5) {
            return new Vector2d(speed_heuristic, angle_heuristic);
        }
        //List<score()> scores = new ArrayList();
        //List<physics.Vector2d> = new ArrayList<>();
        List<Pair> scores = new ArrayList();

        for (int i = 0; i < 10; i++) {
            double new_speed = adjust_speed(speed_heuristic);
            double new_angle = adjust_angle(angle_heuristic);
            Vector2d new_velocity = new Vector2d(new_speed, new_angle);
            new_score = score();                   //new_score = score(putting.take_shot(new_velocity, true));
            scores.add(new Pair<Double, Vector2d>(new_score, new_velocity));

            if (inHole() == true)
                return new_velocity;
        }

        //iterate over list
        if (depth < 5) {
            for (int i = 0; i < scores.size();i++){
                //replace scores with the scores of the childs + give positions
                Vector2d tempVel = (Vector2d) scores.get(i).getValue();
                scores.set(i, new Pair<Double, Vector2d>((Double) scores.get(i).getKey(),getpath(tempVel.get_x(), tempVel.get_y(), (Double) scores.get(i).getKey(), depth + 1)));
                //make order go from little to big
                if (inHole() == true) {
                    return (Vector2d) scores.get(i).getValue();
                }
                else {
                    return null;
                }
            }
        }

        return null;
    }


    public boolean HoleInOnePossible(){ // calculate if the bot can reach the destination

        putting.take_shot(Tools.velFromAngle(90, putting.get_course().get_maximum_velocity()), true);

        Double shotDis = putting.get_ball_position().get_x(); // by shooting the ball at 90 degrees, the x value is the maximum distance it can shoot
        //System.out.print(shotDis);
        Double disFlag = Math.sqrt(Math.pow((end.get_x()-putting.get_course().get_hole_tolerance()), 2) + Math.pow((end.get_y()-putting.get_course().get_hole_tolerance()), 2));
        //System.out.print(disFlag);
        Double shots = (disFlag/shotDis); // calculate the amount of shots needed to score the hole in one based on distance
        // if the value is higher than 1, it can not shoot the hole in one
        if (shots > 1) {
            return false;
        }
        else {
            return true;
        }
    }


    public boolean finishGame() {
        if (HoleInOnePossible() == false) {
            return false;
        }
        else {
            return true;
        }
    }



        /*rule out everything except scores, and then find minimum

        int minimum_score = Math.min(new_score);
        return <double, double>(speed_heuristic, angle_heuristic, score);
        */


    // int tries = 0;
    // double start_speed, start_angle;
    // double upperbound = new physics.Vector2d
    // double lowerbound = new physics.Vector2d
    // assert start is not finnish
    // while start != finnish && tries < 10
    // calculate vector start to finnish
    // take a shot --> return land position
    // if landing == finnish
    //  return True
    // else try again
    //--------------------------------------
    // return False

   /* public boolean finishGame() {

    }
    */
}