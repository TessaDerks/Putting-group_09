package entities;

import org.lwjglx.util.vector.Vector3f;

public class Light {

    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation = new Vector3f(1,0,0);

// Constructor 1 for light: without Attenuation

    /**
     *
     * @param position
     * @param colour
     */
    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }
// Constructor 2 for Light: with Attenuation (if light travels a big distance, it becomes dimmer with this constructor)

    /**
     *
     * @param position
     * @param colour
     * @param attenuation
     */
    public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }
// Getters and Setters
    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
