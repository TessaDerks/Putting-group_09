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
     * Constructor for the TerrainRenderer
     * this method starts the terrainShader and loads in the projection matrix and connect all the Textures.
     * @param shader TerrainShader, this is the shader for the terrain
     * @param projectionMatrix Matrix4f, a projection matrix that is being loaded into the shader.
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
     * Render method for the terrain
     * this renders all the terrains in a list of terrains (can be used to render multiple terrains).
     * @param terrains List<Terrain>, this is the list of terrains that will be rendered on screen.
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

    /**
     * This method prepares the terrain for rendering
     * this is where all the bindings of the vertexArrays happens and texturing of the terrain
     * @param terrain Terrain, takes in the terrain that will be prepared for rendering, binds the textures and enables vertex attributes.
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


    /**
     * This method binds all the textures to a specific colour.
     * @param terrain Terrain, takes in the texturePack of the terrain where the textures will be binded on.
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

    private void loadModelMatrix(@NotNull Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
