package GUI;

import org.lwjglx.util.vector.Vector2f;

public class GUITexture {

    private int texture;
    private Vector2f position;
    private Vector2f scale;

    /**
     * create texture for small GUI that appears when ball landed at hole
     * @param texture int, texture that is loaded in Main
     * @param position Vector2f, position of texture for win GUI
     * @param scale Vector2f, scale of texture for win GUI
     */
    public GUITexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
