package entities;


import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Keyboard;
import org.lwjglx.input.Mouse;
import org.lwjglx.util.vector.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0,30,0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horDistance, float vertDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horDistance*Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horDistance*Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + vertDistance;
        this.yaw = 180 - (player.getRotY()+angleAroundPlayer);
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        if(Input.isKeyDown(GLFW.GLFW_KEY_UP)){
            distanceFromPlayer += 1;
        }
        else if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)){
            distanceFromPlayer -= 1;
        }



    }

    private void calculatePitch(){
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT)){
            double pitchChange = Input.getMouseY() * 0.001;
            pitch -= pitchChange;

        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
            double pitchChange = Input.getMouseY() * 0.001;
            pitch += pitchChange;

        }
    }

    private void calculateAngleAroundPlayer(){

        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
            double angleChange = Input.getMouseX() *0.001;
            angleAroundPlayer -= angleChange;

        }

        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)){
            double angleChange = Input.getMouseX() *0.001;
            angleAroundPlayer += angleChange;

        }

    }

    public void invertPitch(){
        this.pitch = -pitch;
    }

}