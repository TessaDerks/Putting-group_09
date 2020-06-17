package physics;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeightMap implements Function2d {

    private BufferedImage heightMap = null;
    private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
    private static final float MAX_HEIGHT = 100;

    public HeightMap(String heightmap){
        try {
            heightMap = ImageIO.read(new File("res/" + heightmap + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double evaluate(Vector2d p) {
        double z = Main.terrain.getHeightOfTerrain((float) p.get_x(), (float) p.get_y());
        return z;
    }

    @Override
    public Vector2d gradient(Vector2d p) {
        double dx = (this.evaluate(new Vector2d(p.get_x() + delta,p.get_y())) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        double dy = (this.evaluate(new Vector2d(p.get_x(),p.get_y() + delta)) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        dx = Tools.advRound(dx,4);
        dy = Tools.advRound(dy,4);
        return new Vector2d(dx,dy);
    }

}
