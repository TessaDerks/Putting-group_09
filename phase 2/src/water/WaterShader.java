package water;


import entities.Camera;
import maths.Maths;
import org.lwjglx.util.vector.Matrix4f;
import shaders.ShaderProgram;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/water/waterVertex.txt";
	private final static String FRAGMENT_FILE = "src/water/waterFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
// constructor for water Shader
	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
// get the location of the Uniforms
	@Override
	protected void getAllUniformLocation() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("location_moveFactor");

	}
// connection of texture Units
	public void connectTextureUnits(){
		super.loadInt(location_reflectionTexture,0);
		super.loadInt(location_refractionTexture,1);
		super.loadInt(location_dudvMap,2);
	}

	public void loadMoveFactor(float factor){
		super.loadFloat(location_moveFactor, factor);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}


}
