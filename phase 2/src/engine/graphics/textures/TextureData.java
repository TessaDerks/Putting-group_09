package engine.graphics.textures;

import java.nio.ByteBuffer;

public class TextureData {

    private int width;
    private int height;
    private ByteBuffer buffer;

    /**
     * Constructor for a TextureData object
     * assigns a buffer for the textureData and the width and height of the texture files.
     * @param buffer ByteBuffer
     * @param width int
     * @param height int
     */
    public TextureData(ByteBuffer buffer, int width, int height){
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer getBuffer(){
        return buffer;
    }

}
