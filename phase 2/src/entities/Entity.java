package entities;

import engine.graphics.models.TexturedModel;
import org.lwjglx.util.vector.Vector3f;

public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX,rotY,rotZ;
    private float scale;
    private int textureIndex = 0;


    /**
     *  create entity
     * @param model TexturedModel
     * @param position Vector3f, position of entity in terrain
     * @param rotX float, rotation to X axis
     * @param rotY float, rotation to Y axis
     * @param rotZ float, rotation to Z axis
     * @param scale float, scale of entity
     */
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public float getTextureXOffset(){
        int column = textureIndex%model.getTexture().getNumberOfRows();
        return (float)column/(float)model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset(){
        int row = textureIndex/model.getTexture().getNumberOfRows();
        return (float)row/(float)model.getTexture().getNumberOfRows();
    }

    public TexturedModel getModel() { return model; }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

}
