package engine.graphics;

import engine.graphics.models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector4f;
import shaders.StaticShader;
import shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrain.Terrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 10000000f;

    private static final float RED = 0.5444f;
    private static final float GREEN = 0.62f;
    private static final float BLUE = 0.69f;

    private Matrix4f projectionMatrix;
    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();
    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();
    private SkyboxRenderer skyboxRenderer;
// Constructor for MasterRenderer

    /**
     *
     * @param loader
     */
    public MasterRenderer(Loader loader) {
        enableCulling();
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
    }

// enable culling
    public static void enableCulling(){
        GL46.glEnable(GL46.GL_CULL_FACE);
        GL46.glCullFace(GL46.GL_BACK);
    }
// disable Culling
    public static void disableCulling(){
        GL46.glDisable(GL46.GL_CULL_FACE);
    }
// render method

    /**
     *
     * @param lights
     * @param camera
     * @param clipPlane
     */
    public void render(List<Light> lights, Camera camera, Vector4f clipPlane){
        prepare();

        shader.start();
        shader.loadClipPlane(clipPlane);
        shader.loadSkyColour(RED,GREEN,BLUE);
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();

        terrainShader.start();
        terrainShader.loadClipPlane(clipPlane);
        terrainShader.loadSkyColour(RED,GREEN,BLUE);
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        skyboxRenderer.render(camera, RED,GREEN,BLUE);

        terrains.clear();
        entities.clear();
    }
// add terrain to list of terrains

    /**
     *
     * @param terrain
     */
    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }
// getter function for ProjectionMatrix
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
// Adding Entity to a list of entities

    /**
     *
     * @param entity
     */
    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null){
            batch.add(entity);
        } else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel,newBatch);

        }
    }
// cleanup of shaders
    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
        skyboxRenderer.getShader().cleanUp();
    }
// preparing screen to be rendered onto.
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL46.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }
// creation of a projection Matrix
    private void createProjectionMatrix(){
        float aspectRatio = (float) 1280 / (float) 720;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}
