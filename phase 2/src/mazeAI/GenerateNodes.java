package mazeAI;

import org.jetbrains.annotations.NotNull;
import physics.Vector2d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateNodes{

    private Graph<CheckPoint> maze;
    private Set<CheckPoint> checkPointSet = new HashSet<>();
    private Map<String, Set<String>> connections = new HashMap<>();
    private final Vector2d end;
    private final double k;
    private int compensate;

    /**
     *
     * @param _start Vector2d
     * @param _end Vector2d
     * @param _k double, k => 0.5, determines distance between nodes
     * @param _compensate int, compensate > 0, amount of nodes behind the end
     */
    public GenerateNodes(@NotNull Vector2d _start, Vector2d _end, double _k, int _compensate){
        end = _end;
        k = _k;
        compensate = _compensate;

        // Create nodes
        checkPointSet.add(new CheckPoint(_start.get_x(), _start.get_y()));
        Vector2d n0,n1,n2,n3,n4,n5,n6,n7;
        n0 = new Vector2d(_start.get_x()-k, _start.get_y()+k);
        n1 = new Vector2d(_start.get_x(), _start.get_y()+k);
        n2 = new Vector2d(_start.get_x()+k, _start.get_y()+k);
        n3 = new Vector2d(_start.get_x()+k, _start.get_y());
        n4 = new Vector2d(_start.get_x()+k, _start.get_y()-k);
        n5 = new Vector2d(_start.get_x(), _start.get_y()-k);
        n6 = new Vector2d(_start.get_x()-k, _start.get_y()-k);
        n7 = new Vector2d(_start.get_x()-k, _start.get_y());
        recursion(n0,0);
        recursion(n1,1);
        recursion(n2,2);
        recursion(n3,3);
        recursion(n4,4);
        recursion(n5,5);
        recursion(n6,6);
        recursion(n7,7);
        
        // Connect nodes
        for(CheckPoint c : checkPointSet){
            hingeNode(c);
        }
        
        maze = new Graph<>(checkPointSet,connections);
    }

    /**
     *
     * @param c Vector2d, position from where you want to generate the nodes
     * @param p int, i <= 0 <= 7, index of Checkpoint
     */
    public void recursion(@NotNull Vector2d c, int p){
        // stop condition
        if(Math.abs(c.get_x()) == Math.abs(end.get_x())+(k*(compensate+1)) || Math.abs(c.get_y()) == Math.abs(end.get_y())+(k*(compensate+1))){
            return;
        }
        checkPointSet.add(new CheckPoint(c.get_x(),c.get_y()));
        if(p == 0){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()-k,c.get_y())),cToId(new Vector2d(c.get_x()-k,c.get_y()+k)),cToId(new Vector2d(c.get_x(),c.get_y()+k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x()-k,c.get_y()),7);
            recursion(new Vector2d(c.get_x()-k,c.get_y()+k),0);
            recursion(new Vector2d(c.get_x(),c.get_y()+k),1);
        }
        else if(p == 1){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x(),c.get_y()+k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x(),c.get_y()+k),1);
        }
        else if(p == 2){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()+k,c.get_y())),cToId(new Vector2d(c.get_x()+k,c.get_y()+k)),cToId(new Vector2d(c.get_x(),c.get_y()+k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x(),c.get_y()+k),1);
            recursion(new Vector2d(c.get_x()+k,c.get_y()+k),2);
            recursion(new Vector2d(c.get_x()+k,c.get_y()),3);
        }
        else if(p == 3){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()+k,c.get_y()))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x()+k,c.get_y()),3);
        }
        else if(p == 4){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()+k,c.get_y())),cToId(new Vector2d(c.get_x()+k,c.get_y()-k)),cToId(new Vector2d(c.get_x(),c.get_y()-k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x()+k,c.get_y()),3);
            recursion(new Vector2d(c.get_x()+k,c.get_y()-k),4);
            recursion(new Vector2d(c.get_x(),c.get_y()-k),5);
        }
        else if(p == 5){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x(),c.get_y()-k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x(),c.get_y()-k),5);
        }
        else if(p == 6){
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()-k,c.get_y())),cToId(new Vector2d(c.get_x()-k,c.get_y()-k)),cToId(new Vector2d(c.get_x(),c.get_y()-k))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x(),c.get_y()-k),5);
            recursion(new Vector2d(c.get_x()-k,c.get_y()-k),6);
            recursion(new Vector2d(c.get_x()-k,c.get_y()),7);
        }
        else{
            //connections.put(cToId(c), Stream.of(cToId(new Vector2d(c.get_x()-k,c.get_y()))).collect(Collectors.toSet()));
            recursion(new Vector2d(c.get_x()-k,c.get_y()),7);
        }
    }

    /**
     *
     * @param c Checkpoint, generate connections from this checkpoint
     */
    public void hingeNode(@NotNull CheckPoint c){
        Vector2d n0, n1, n2, n3, n4, n5, n6, n7;

        n0 = new Vector2d(c.getX()-k,c.getY()+k);
        n1 = new Vector2d(c.getX(),c.getY()+k);
        n2 = new Vector2d(c.getX()+k,c.getY()+k);
        n3 = new Vector2d(c.getX()+k,c.getY());
        n4 = new Vector2d(c.getX()+k,c.getY()-k);
        n5 = new Vector2d(c.getX(),c.getY()-k);
        n6 = new Vector2d(c.getX()-k,c.getY()-k);
        n7 = new Vector2d(c.getX()-k,c.getY());

        Set<Vector2d> nSet = Stream.of(n0,n1,n2,n3,n4,n5,n6,n7).collect(Collectors.toSet());
        Set<String> StringSet = new HashSet<>();

        for(Vector2d n : nSet) {
            if (!(Math.abs(n.get_x()) > Math.abs(end.get_x()) + (k * (compensate + 1)) || Math.abs(n.get_y()) > Math.abs(end.get_y()) + (k * (compensate + 1)))) {
                StringSet.add(cToId(n));
            }
        }

        connections.put(cToId(new Vector2d(c.getX(),c.getY())), StringSet);
    }

    /**
     *
     * @param c Vector2d
     * @return string, contains coordinates and returns position as a string
     */
    public String cToId(@NotNull Vector2d c){
        return "("+c.get_x()+","+c.get_y()+")";
    }

    public Graph<CheckPoint> getMaze() {
        return maze;
    }
}