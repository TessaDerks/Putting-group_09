package engine.graphics.textures;

public class TerrainTexture {
    private int textureID;

    public int getTextureID() {
        return textureID;
    }

    /**
     * Constructor for the terrainTexture
     * assigns a textureID to a TerrainTexture object
     * @param textureID int identification for a terrain texture.
     */
    public TerrainTexture(int textureID) {
        this.textureID = textureID;
    }
}
