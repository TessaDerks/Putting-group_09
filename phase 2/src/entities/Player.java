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

public class Player extends Entity {

    private static final float MOVE_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private boolean isInAir1 = false;


    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private int nMoves = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain, Vector2f newPos){
        checkInputs();
        //super.increaseRotation(0, currentTurnSpeed * Window.getFrameTimeSeconds(), 0);
        //float distance = currentSpeed * Window.getFrameTimeSeconds();

                Vector3f newPosition = new Vector3f();
                newPosition.x = newPos.x;
                newPosition.z = newPos.y;
                newPosition.y = terrain.getHeightOfTerrain(newPosition.x, newPosition.z);

                super.setPosition(newPosition);
               // Tools.wait(100);

            nMoves++;





            //float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
            // float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));

            //super.setPosition(newPosition.x, terrainHeight, newPosition.z);

            // upwardsSpeed += GRAVITY* Window.getFrameTimeSeconds();
            //super.increasePosition(0, upwardsSpeed* Window.getFrameTimeSeconds(),0);

           /* float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z)+0.10f;

            if (super.getPosition().y < terrainHeight) {
                upwardsSpeed = 0;
                isInAir1 = false;
                super.getPosition().y = terrainHeight;
            }

            */

    }

    private void jump(){
        if(!isInAir1) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir1 = true;
        }

    }

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
