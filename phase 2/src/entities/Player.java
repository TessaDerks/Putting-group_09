package entities;

import engine.graphics.models.TexturedModel;
import engine.io.Input;
import engine.io.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Keyboard;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import physics.Tools;
import terrain.Terrain;

// player = ball
public class Player extends Entity {

    private static final float MOVE_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float JUMP_POWER = 30;
    private boolean isInAir1 = false;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private int nMoves = 0;

    /**
     *
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

    // updates position

    /**
     *
     * @param terrain
     * @param newPos
     */
    public void move(Terrain terrain, Vector2f newPos){
        checkInputs();

                Vector3f newPosition = new Vector3f();
                newPosition.x = newPos.x;
                newPosition.z = newPos.y;
                newPosition.y = terrain.getHeightOfTerrain(newPosition.x, newPosition.z);

                super.setPosition(newPosition);
               // Tools.wait(100);

            nMoves++;
    }
// jump
    private void jump(){
        if(!isInAir1) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir1 = true;
        }
    }
// checks inputs
    private void checkInputs(){
        if(Input.isKeyDown(GLFW.GLFW_KEY_W)){
            this.currentSpeed = MOVE_SPEED;
        }
        else if (Input.isKeyDown(GLFW.GLFW_KEY_S)){
            this.currentSpeed = -MOVE_SPEED;
        }
        else {
            this.currentSpeed = 0;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }
        else if (Input.isKeyDown(GLFW.GLFW_KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;
        }
        else {
            this.currentTurnSpeed = 0;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            jump();
        }
    }
}
