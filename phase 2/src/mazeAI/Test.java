package mazeAI;

import org.jetbrains.annotations.NotNull;
import physics.Function2d;
import physics.Vector2d;

import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args){
        Function2d f = new Function2d("1");

        GenerateNodes generator = new GenerateNodes(new Vector2d(0,0),new Vector2d(10,10),0.5,3);
        RouteFinder finder = new RouteFinder(generator.getMaze());
        List<CheckPoint> route = finder.findRoute(generator.getMaze().getNode(cToId(new Vector2d(0,0))),generator.getMaze().getNode(cToId(new Vector2d(10,10))));

        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));
    }

    public static String cToId(@NotNull Vector2d c){
        return "("+c.get_x()+","+c.get_y()+")";
    }
}
