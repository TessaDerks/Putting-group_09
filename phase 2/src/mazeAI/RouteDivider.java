package mazeAI;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import physics.Function;
import physics.SimulateMain;
import physics.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class RouteDivider {
    private List<CheckPoint> route;
    private List<Shot> shots = new ArrayList<Shot>();
    private int currentStartNodeIndex = 0;
    private double k;

    public RouteDivider(List<CheckPoint> _route,double _k){
        route = _route;
        k = _k;

        while(currentStartNodeIndex<route.size()-1){
            divideRoute();
        }
    }

    private void divideRoute(){
        int b = currentStartNodeIndex;
        Shot best = new Shot(0,0);
        CheckPoint cb = route.get(b);
        for(int i = b + 1; i < route.size(); i++){
            CheckPoint c = route.get(i);
            Shot s = new Shot(new Vector2d(cb.getX(),cb.getY()),new Vector2d(c.getX(),c.getY()));

            // If no hit or water
            if(checkLine(cb,c)){
                best = s;
                if(i == route.size()-1){
                    currentStartNodeIndex = i;
                    shots.add(best);
                    break;
                }
            }
            else{
                currentStartNodeIndex = i-1;
               shots.add(best);
               return;
            }
        }
    }
    
    private boolean checkLine(@NotNull CheckPoint s, @NotNull CheckPoint e){
        double a = (s.getY()-e.getY())/(s.getX()-e.getX());
        double b = s.getY() - (a * s.getX());
        
        // Iterate through X
        for(double i = s.getX()+k/2; i < e.getX(); i+=k/2){
            Vector2d p = fx(a,i,b);
            if(SimulateMain.simulator.get_course().nodeOnTree(p)|| SimulateMain.simulator.get_engine().get_h().evaluate(p)<0){
                return false;
            }
        }
        
        // Iterate through Y
        for(double i = s.getY()+k/2; i < e.getY(); i+=k/2){
            Vector2d p = fy(a,i,b);
            if(SimulateMain.simulator.get_course().nodeOnTree(p)|| SimulateMain.simulator.get_engine().get_h().evaluate(p)<0){
                return false;
            }
        }
        return true;
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    private @NotNull Vector2d fx(double a, double x, double b){
        return new Vector2d(x,a*x+b);
    }
    
    @Contract(value = "_, _, _ -> new", pure = true)
    private @NotNull Vector2d fy(double a, double y, double b){
        return new Vector2d((y-b)/a,y);
    }

    public List<Shot> getShots() {
        return shots;
    }
}
