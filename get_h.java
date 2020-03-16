public class get_h{
    static double x;
    static double y;
    static double delta = 1e-10;
    static int roundPrecision = 2;

    public static Vector2d get_h(double _x, double _y){
        x = _x;
        y = _y;
        return new Vector2d(deriveX(),deriveY());
    }

    public static double h(double hx, double hy){
        ///double hz = Math.sin(hx) + hx/5 + 1;
        double hz = 1;
        return hz;
    }

    public static double deriveX(){
        double derivativeAns = (h(x + delta,y) - h(x,y))/delta;
        derivativeAns = advRound(derivativeAns,4);
        return derivativeAns;
    }

    public static double deriveY(){
        double derivativeAns = (h(x,y + delta) - h(x,y))/delta;
        derivativeAns = advRound(derivativeAns,4);
        return derivativeAns;
    }

    public static double advRound(double num, int dec){
        num = num * Math.pow(10,dec);
        num = Math.round(num);
        num = num / Math.pow(10,dec);
        return num;
    }
}