package physics;


import java.util.LinkedList;
import java.util.List;

public class PuttingCourse {

    //<editor-fold desc="Global Variables">
    private final Function2d height;
    private final Vector2d flag;
    private final Vector2d start;
    private double mu = 0.3;
    private double vMax = 5;
    private double holeTolerance = 0.2;
    private List<Tree> treeList = new LinkedList<Tree>();
    private List<Sand> sandList = new LinkedList<Sand>();
    private List<Tree> stumpList = new LinkedList<>();
    //</editor-fold>

    /**
     *
     * @param _height
     * @param _flag
     * @param _start
     */
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

    public void addTree(Tree t){
        treeList.add(t);
    }

    public void addSand(Sand s) {
        sandList.add(s);
    }

    public void addStump(Tree t){
        stumpList.add(t);
    }


    //</editor-fold>

    public double get_friction_coefficient(){
        return mu;
    }

    public double get_maximum_velocity(){
        return vMax;
    }

    public double get_hole_tolerance(){
        return holeTolerance;
    }

    public List<Tree> getTreeList(){
        return treeList;
    }

    public List<Sand> getSandList() {
        return sandList;
    }

    public List<Tree> getStumpList() {
        return stumpList;
    }
    /**
     *
     * @param p
     * @return
     */
    public boolean nodeOnTree(Vector2d p) {
        for(Tree t : treeList){
            if(t.AITreeHit(p)){
                return true;
            }
        }
        return false;
    }
    //</editor-fold>
}
