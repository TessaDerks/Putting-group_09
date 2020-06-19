package engine.graphics.textures.objConverter;

import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Vector3f;

public class Vertex {

    private static final int NO_INDEX = -1;

    private Vector3f position;
    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private Vertex duplicateVertex = null;
    private int index;
    private float length;

    /**
     * This is a constructor for a Vertex.
     * Here you can create a vertex with an index number and position.
     * @param index int index of the Vertex.
     * @param position Vector3f position of the vertex.
     */
    public Vertex(int index, @NotNull Vector3f position){
        this.index = index;
        this.position = position;
        this.length = position.length();
    }

    /**
     * This checks if a vertex has the same textures and normals.
     * @param textureIndexOther int texture index of the texture you want to check with.
     * @param normalIndexOther int normal index of the normal you want to check with.
     * @return boolean true if texture and normal are the same false if not.
     */
    public boolean hasSameTextureAndNormal(int textureIndexOther,int normalIndexOther){
        return textureIndexOther==textureIndex && normalIndexOther==normalIndex;
    }

    public void setTextureIndex(int textureIndex){
        this.textureIndex = textureIndex;
    }

    public void setNormalIndex(int normalIndex){
        this.normalIndex = normalIndex;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public int getNormalIndex() {
        return normalIndex;
    }

    public Vertex getDuplicateVertex() {
        return duplicateVertex;
    }

    public void setDuplicateVertex(Vertex duplicateVertex) {
        this.duplicateVertex = duplicateVertex;
    }

    public int getIndex() { return index; }

    public float getLength(){
        return length;
    }

    public boolean isSet(){
        return textureIndex!=NO_INDEX && normalIndex!=NO_INDEX;
    }

}