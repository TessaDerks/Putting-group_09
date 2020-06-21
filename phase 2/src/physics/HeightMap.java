package physics;

import maths.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import terrain.Terrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Stijn Hennissen
 */
public class HeightMap implements Function2d {

    private BufferedImage heightMap = null;
    private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
    private static final float MAX_HEIGHT = 40;
    private float[][] heights;


    /**
     * create heightmap function
     * @param heightmap String, name of heightmap file
     */
    public HeightMap(String heightmap){
        try {
            heightMap = ImageIO.read(new File("res/" + heightmap + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateHeights();
    }

    /**
     * get height at certain position
     * @param p Vector2d, position on terrain
     * @return double, height at given position
     */
    @Override
    public double evaluate(@NotNull Vector2d p) {
        return getHeightOfTerrain((float) p.get_x(), (float) p.get_y());
    }

    @Override
    public Vector2d gradient(@NotNull Vector2d p) {
        double dx = (this.evaluate(new Vector2d(p.get_x() + delta,p.get_y())) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        double dy = (this.evaluate(new Vector2d(p.get_x(),p.get_y() + delta)) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        dx = Tools.advRound(dx,4);
        dy = Tools.advRound(dy,4);
        return new Vector2d(dx,dy);
    }

    public void generateHeights(){
        int VERTEX_COUNT = heightMap.getHeight();
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++) {
                float height = getHeight(j,i) ;
                heights[j][i] = height;
            }
        }
    }

    /**
     * get height at any point from double array with all heights
     * @param worldX float
     * @param worldZ float
     * @return float, height of terrain at given index
     */
    public float getHeightOfTerrain(float worldX, float worldZ){
        float gridSquareSize = Terrain.SIZE/((float)heights.length -1);
        int gridX = (int) Math.floor(worldX /gridSquareSize);
        int gridZ = (int) Math.floor(worldZ /gridSquareSize);
        if(gridX >= heights.length -1 || gridZ >= heights.length -1 || gridX < 0 || gridZ < 0){
            return 0;
        }
        float xCoord = (worldX % gridSquareSize)/gridSquareSize;
        float zCoord = (worldZ % gridSquareSize)/gridSquareSize;
        float answer;
        if(xCoord <= (1-zCoord)){
            answer = Maths
                    .barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));

        } else{
            answer = Maths
                    .barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));

        }
        return answer;

    }

    /**
     * get height of heightmap at given index
     * @param x int
     * @param y int
     * @return float, height at given index
     */
    private float getHeight(int x, int y){
        if(x<0 || x>=heightMap.getHeight() || y<0 || y>=heightMap.getHeight()){
            return 0;
        }
        float height = heightMap.getRGB(x, y);
        height += MAX_PIXEL_COLOUR/2f;
        height /= MAX_PIXEL_COLOUR/2f;
        height *= MAX_HEIGHT;
        return height;

    }

}
