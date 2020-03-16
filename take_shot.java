import java.util.Scanner;
import java.util.ArrayList;

public class take_shot {
    public static Vector2d startingPos;
    public static Vector2d startingVel;

    public static Vector2d treeHitVel;

    public static void take_shot(Vector2d _pos, Vector2d _vel){
        startingPos = _pos;
        startingVel = _vel;

        Tracker tracker = new Tracker(startingPos,startingVel);

        // While ball is still moving.
        while(tracker.get_v().getX() != 0 || tracker.get_v().getY() != 0){
            tracker.actTimestep(0.01);

            // Printing data.
            Vector2d cp = tracker.get_p();
            Vector2d cv = tracker.get_v();
            //System.out.println("t=" + tracker.get_t() + ": p(" + cp.getX() + "," + cp.getY() + ") v(" + cv.getX() + "," + cv.getY() + ")");

            // If ball hits water.
            if(get_h.h(cp.getX(),cp.getY()) < 0){
                PuttingSimulator.setBallPosition(PuttingSimulator.getLastPosition());
                System.out.println("water");
                break;
            }

            ArrayList<Tree> trees = PuttingSimulator.getTrees();
            for(int i = 0; i < trees.size(); i++){
                if(trees.get(i).treeHit(cp)){
                    System.out.println("tree hit" + cp.getX() + " " + cp.getY());
                    double rc;
                    double newAngle;
                    if(PuttingSimulator.getLastAngle() == 0 && PuttingSimulator.getBallPosition().getX() == trees.get(i).getP().getX()){
                        newAngle = 180;
                    }
                    else if(PuttingSimulator.getLastAngle() == 180 && PuttingSimulator.getBallPosition().getX() == trees.get(i).getP().getX()){
                        newAngle = 0;
                    }
                    else{
                        rc = (trees.get(i).getP().getY() - cp.getY()) / (trees.get(i).getP().getX() - cp.getX());
                        newAngle = 2 * Math.tan(rc) - PuttingSimulator.getLastAngle() - 90;
                    }

                    double ntv = Math.sqrt(Math.pow(cv.getX(),2) + Math.pow(cv.getY(),2));
                    Vector2d nv = PuttingSimulator.velFromAngle(newAngle,ntv);
                    tracker = new Tracker(cp,nv);
                }
            }

            PuttingSimulator.setBallPosition(cp);
        }
    }
}
