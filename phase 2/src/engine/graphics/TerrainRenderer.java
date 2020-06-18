package engine.graphics;

import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.graphics.textures.TerrainTexturePack;
import entities.Entity;
import maths.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.*;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import shaders.TerrainShader;
import terrain.Terrain;

import java.util.List;

public class TerrainRenderer {

    private TerrainShader shader;
// Constructor for a Terrain Renderer

    /**
     *
     * @param shader
     * @param projectionMatrix
     */
    public TerrainRenderer(@NotNull TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }
// render method

    /**
     *
     * @param terrains
     */
    public void render(@NotNull List<Terrain> terrains) {
        for (Terrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }
// preparation of terrain to be rendered onto screen.

    /**
     *
     * @param terrain
     */
    private void prepareTerrain(@NotNull Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // ======================== Multi Texture ======================
        bindTextures(terrain);
        shader.loadShineVariables(1,0);

        // ========================= single Texture ===================
        //shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        //GL46.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
    }
// binding of textures.

    /**
     *
     * @param terrain
     */
    private void bindTextures(@NotNull Terrain terrain){
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
    }
// unbinding of textures
    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
// loading of a terrain onto a transformation matrix

    /**
     *
     * @param terrain
     */
    private void loadModelMatrix(@NotNull Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
