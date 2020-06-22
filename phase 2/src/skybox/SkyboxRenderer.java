package skybox;

import engine.graphics.Loader;
import engine.graphics.models.RawModel;
import engine.io.Window;
import entities.Camera;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL46;
import org.lwjglx.util.vector.Matrix4f;

public class SkyboxRenderer {

    private static final float SIZE = 500f;

    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };


    private static final String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
    private static final String[] NIGHT_TEXTURE_FILES = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"};

    private RawModel cube;
    private int texture;
    private int nightTexture;
    private SkyboxShader shader;
    private float time = 0;

// returns shader for the skybox
    public SkyboxShader getShader() {
        return shader;
    }
// Constructor for skybox

    /**
     * Render method for the skybox.
     * @param loader Loader, loader that will be used to render the skybox with.
     * @param projectionMatrix Matrix4f, projection matrix that will be used for the skybox.
     */
    public SkyboxRenderer(@NotNull Loader loader, Matrix4f projectionMatrix){

        cube = loader.loadToVAO(VERTICES, 3);
        texture = loader.loadCubeMap(TEXTURE_FILES);
        nightTexture = loader.loadCubeMap(NIGHT_TEXTURE_FILES);
        shader = new SkyboxShader();
        shader.start();
        shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
// Render method

    /**
     * Method that renders the shaders of the skybox.
     * @param camera Camera, camera that will be set as the viewMatrix.
     * @param r float, value of red colour that will be loaded as the Fog colour.
     * @param g float, value of green colour that will be loaded as the Fog colour.
     * @param b float, value of blue colour that will be loaded as the Fog colour.
     */
    public void render(Camera camera, float r, float g, float b){

        shader.start();
        shader.loadViewMatrix(camera);
        shader.loadFogColour(r,g,b);
        GL46.glDepthMask(false);
        GL46.glDepthRange(0f ,1f);
        GL46.glBindVertexArray(cube.getVaoID());
        GL46.glEnableVertexAttribArray(0);
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        bindTextures();
        GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, cube.getVertexCount());
        GL46.glDisableVertexAttribArray(0);
        GL46.glBindVertexArray(0);

        GL46.glDepthRange(0f ,1f);
        GL46.glDepthMask(true);
        shader.stop();
    }
// binding of textures
    private void bindTextures(){
        time += Window.getFrameTimeSeconds()*1000;
        time%=24000;
        float blendFactor = -((time/1000f)-13)*((time/1000f)-13) * 0.02f + 1;

        if(blendFactor < 0) blendFactor = 0;

        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        GL46.glBindTexture(GL46.GL_TEXTURE_CUBE_MAP, texture);
        GL46.glActiveTexture(GL46.GL_TEXTURE1);
        GL46.glBindTexture(GL46.GL_TEXTURE_CUBE_MAP, nightTexture);
        shader.loadBlendFactor(blendFactor);
    }

}
