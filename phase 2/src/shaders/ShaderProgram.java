package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;
import org.lwjglx.BufferUtils;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

// Constructor for Shader Program

    /**
     * Constructor for a shader program.
     * @param vertexFile String, takes in the vertex shader file.
     * @param fragmentFile String, takes in the fragment shader file.
     */
    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocation();
    }

    protected abstract void getAllUniformLocation();

    /**
     * Method that takes in the uniform locations
     * @param uniformName String, takes in the uniform Name.
     * @return int, returns the uniformLocation of the String parameter.
     */
    protected int getUniformLocation(String uniformName){
        return GL46.glGetUniformLocation(programID,uniformName);
    }

    public void start(){
        GL20.glUseProgram(programID);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

// clean up of the shaders.
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    /**
     *
     * @param attribute
     * @param variableName
     */
    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    /**
     * Method that loads up an integer
     * @param location int, location of the uniform.
     * @param value int, value that you want to load up.
     */
    protected void loadInt(int location, int value){
        GL46.glUniform1i(location, value);
    }

    /**
     * Method that loads up a float.
     * @param location int, location of the uniform.
     * @param value float, value that you want to load up.
     */
    protected void loadFloat(int location, float value){
        GL46.glUniform1f(location, value);
    }

    /**
     * Method that loads up a vector.
     * @param location int, location of the uniform.
     * @param vector Vector3f, value that you want to load up.
     */
    protected void loadVector(int location, @NotNull Vector3f vector){
        GL46.glUniform3f(location, vector.x,vector.y,vector.z);
    }

    /**
     * Method that loads up a 2DVector.
     * @param location int, location of the uniform.
     * @param vector Vector2f, value that you want to load up.
     */
    protected void load2DVector(int location, @NotNull Vector2f vector){
        GL46.glUniform2f(location, vector.x,vector.y);
    }

    /**
     * Method that loads up a vector.
     * @param location int, location of the uniform.
     * @param vector Vector4f, value that you want to load up.
     */
    protected void loadVector(int location, @NotNull Vector4f vector){
        GL46.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    /**
     * Method that loads up a Boolean.
     * @param location int, location of the uniform.
     * @param value boolean, value that you want to load up.
     */
    protected void loadBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        GL46.glUniform1f(location, toLoad);
    }

    /**
     * method that loads up the matrix of a uniform
     * @param location int, location
     * @param matrix Matrix4f, matrix that you want to load up.
     */
    protected void loadMatrix(int location, @NotNull Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL46.glUniformMatrix4fv(location,false, matrixBuffer);
    }
// loads up shaders

    /**
     * Method that loads up a string
     * @param file String, name of the shader file you want to load.
     * @param type int, type of the shader you want to load.
     * @return returns a shaderID
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

}