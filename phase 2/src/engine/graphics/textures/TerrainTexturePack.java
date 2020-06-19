package engine.graphics.textures;

public class TerrainTexturePack {
    private TerrainTexture backgroundTexture;
    private TerrainTexture rTexture;
    private TerrainTexture gTexture;
    private TerrainTexture bTexture;

    public TerrainTexture getBackgroundTexture() {
        return backgroundTexture;
    }

    public TerrainTexture getrTexture() {
        return rTexture;
    }

    public TerrainTexture getgTexture() {
        return gTexture;
    }

    public TerrainTexture getbTexture() {
        return bTexture;
    }

    /**
     * Constructor for the TerrainTexturePack
     * This creates a Texture Pack for the terrain.
     * @param backgroundTexture TerrainTexture background colour texture
     * @param rTexture TerrainTexture red colour texture
     * @param gTexture TerrainTexture green colour texture
     * @param bTexture TerrainTexture blue colour texture
     */
    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
        this.backgroundTexture = backgroundTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }


}
