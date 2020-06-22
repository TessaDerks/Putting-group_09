package shaders;

import entities.Camera;
import entities.Light;
import maths.Maths;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import java.util.List;

public class StaticShader extends ShaderProgram{

    private static final int MAX_LIGHTS = 4;
    private static final String VERTEX_FILE = "src/shaders/vertexShader";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPosition;
    private int[] location_lightColour;
    private int[] location_attenuation;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;
    private int location_plane;

// constructor for a Static Shader
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

// get location of uniforms
    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColour = super.getUniformLocation("skyColour");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");
        location_plane = super.getUniformLocation("plane");
        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];

        for(int i=0; i<MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]");
            location_lightColour[i] = super.getUniformLocation("lightColour["+i+"]");
            location_attenuation[i] = super.getUniformLocation("attenuation["+i+"]");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    /**
     * Method that loads up the clipping plane.
     * @param plane Vector4f, this is the plane where you want to start the clipping.
     */
    public void loadClipPlane(Vector4f plane){
        super.loadVector(location_plane, plane);
    }

    /**
     * Method that loads up the number of rows
     * @param numberOfRows int, this is the number of rows you want to load up.
     */
    public void loadNumberOfRows (int numberOfRows){
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    /**
     * Method that loads up the Offset
     * @param x float, the x coordinate of the offset loads this into a new Vector2f.
     * @param y float, the y coordinate of the offset loads this into a new Vector2f.
     */
    public void loadOffset (float x, float y){
        super.load2DVector(location_offset, new Vector2f(x,y));
    }

    /**
     * Method that loads up the colour of the Sky
     * @param r float, value that represents the red colour.
     * @param g float, value that represents the green colour.
     * @param b float, value that represents the blue colour.
     */
    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r,g,b));
    }

    /**
     * Method that loads up fake lighting
     * @param useFake boolean, true if you want to load up fake lighting.
     */
    public void loadFakeLightingVariable(boolean useFake){
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    /**
     * Method that loads up the shine
     * @param damper float, value that represents the inhibition of the shine.
     * @param reflectivity float, value that represents the reflectivity of the shine.
     */
    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    /**
     * method that loads the transformation Matrix
     * @param matrix Matrix4f, this is the matrix that represents transformations.
     */
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }

    /**
     * Method that loads the viewMatrix
     * @param camera Camera, assigns the camera as the viewMatrix and loads it up.
     */
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    /**
     * Loads in a list of lights for the shaders.
     * @param lights List<Light>, a list of all the light sources in the game.
     */
    public void loadLights(List<Light> lights){
        for (int i=0;i<MAX_LIGHTS;i++){
            if(i<lights.size()){
                super.loadVector(location_lightPosition[i],lights.get(i).getPosition());
                super.loadVector(location_lightColour[i],lights.get(i).getColour());
                super.loadVector(location_attenuation[i],lights.get(i).getAttenuation());
            } else{
                super.loadVector(location_lightPosition[i],new Vector3f(0,0,0));
                super.loadVector(location_lightColour[i],new Vector3f(0,0,0));
                super.loadVector(location_attenuation[i],new Vector3f(1,0,0));
            }
        }
    }

    /**
     * Method that loads up the projection Matrix
     * @param projection Matrix4f, this is a matrix containing the projection.
     */
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix,projection);
    }

}
