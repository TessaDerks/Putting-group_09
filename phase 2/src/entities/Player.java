package entities;

import engine.graphics.models.TexturedModel;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import terrain.Terrain;


public class Player extends Entity {

    /**
     * create golf ball
     * @param model
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }


    /**
     * move golf ball to new position in terrain
     * @param terrain Terrain, playfield of game
     * @param newPos Vector2f, new position of golf ball
     */
    public void move(@NotNull Terrain terrain, @NotNull Vector2f newPos){
        Vector3f newPosition = new Vector3f();
        newPosition.x = newPos.x;
        newPosition.z = newPos.y;
        newPosition.y = terrain.getHeightOfTerrain(newPosition.x, newPosition.z);

        super.setPosition(newPosition);
    }

}
