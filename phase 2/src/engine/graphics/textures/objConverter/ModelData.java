package engine.graphics.textures.objConverter;

public class ModelData {

    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;

    /**
     * Constructor for ModelData
     * This contains all the data for a model, like vertices, texture coordinates, normal data, indices data and furthest point data
     * @param vertices float[] Array with all the coordinates of the vertices
     * @param textureCoords float[] array with all the coordinates of the textures
     * @param normals float[] array with all the coordinates of the normals
     * @param indices int[] array with all the indices.
     * @param furthestPoint float float value with the furthest point.
     */
    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
                     float furthestPoint) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }

}