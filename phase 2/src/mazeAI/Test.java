package mazeAI;

import org.jetbrains.annotations.NotNull;
import physics.Function;
import physics.Vector2d;

import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args){
        Function f = new Function("cos ( x ) + cos ( y ) + 1", 1);

        GenerateNodes generator = new GenerateNodes(new Vector2d(0,0),new Vector2d(12,12),0.5,3);
        RouteFinder finder = new RouteFinder(generator.getMaze());
        List<CheckPoint> route = finder.findRoute(generator.getMaze().getNode(cToId(new Vector2d(0,0))),generator.getMaze().getNode(cToId(new Vector2d(12,12))));

        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));

        RouteDivider divider = new RouteDivider(route,0.5);

        List<Shot> shots = divider.getShots();

        System.out.println();
        for(Shot s : shots){
            System.out.print(s);
        }
    }

    /**
     *
     * @param c Vector2d, which you want to convert into a string
     * @return String, converted Vector2d
     */
    public static @NotNull String cToId(@NotNull Vector2d c){
        return "("+c.get_x()+","+c.get_y()+")";
    }
}
