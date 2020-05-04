package physics;


public class PuttingCourse {

    //<editor-fold desc="Global Variables">
    private Function2d height;
    private Vector2d flag;
    private Vector2d start;
    private double mu = 0.131;
    private double vMax = 5;
    private double holeTolerance = 0.2;
    //</editor-fold>

    public PuttingCourse(Function2d _height, Vector2d _flag, Vector2d _start){
        height = _height;
        flag = _flag;
        start = _start;
    }

    //<editor-fold desc="Setters">
    public void set_mu(double _mu){
        mu = _mu;
    }

    public void set_vMax(double _vMax){
        vMax = _vMax;
    }

    public void set_holeTolerance(double _tol){
        holeTolerance = _tol;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public Function2d get_height(){
        return height;
    }

    public Vector2d get_flag_position(){
        return flag;
    }

    public Vector2d get_start_position(){
        return start;
    }

    public double get_friction_coefficient(){
        return mu;
    }

    public double get_maximum_velocity(){
        return vMax;
    }

    public double get_hole_tolerance(){
        return holeTolerance;
    }
    //</editor-fold>
}
