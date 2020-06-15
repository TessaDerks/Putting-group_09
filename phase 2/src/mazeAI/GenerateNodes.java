/**
node om de k meter
node voor (xs,ys)
volgende nodes voor (xs-k,ys) (xs+k,ys) (xs,ys-k) (xs,ys+k) + hoekpunten
continue generating until endpoint
 **/

package mazeAI;

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
    private Vector2d start;
    private Vector2d end;
    private double k;
    private int compensate;

    public GenerateNodes(Vector2d _start, Vector2d _end, double _k, int _compensate){
        start = _start;
        end = _end;
        k = _k;
        compensate = _compensate;

        // recursion zwick

        for(CheckPoint c : checkPointSet){
            hingeNode(c);
        }
    }

    public void recursion(Vector2d c, int p){
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
        return;
    }

    public void hingeNode(CheckPoint c){
        Vector2d n1 = new Vector2d(c.getX()-k,c.getY()+k);
        if(!(Math.abs(n1.get_x()) > Math.abs(end.get_x())+(k*(compensate+1)) || Math.abs(n1.get_y()) > Math.abs(end.get_y())+(k*(compensate+1)))){

        }
    }

    public String cToId(Vector2d c){
        return "("+c.get_x()+","+c.get_y()+")";
    }
}