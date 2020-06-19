package engine.graphics.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class RawModel {

	private int vaoID;
	private int vertexCount;

	/**
	 * Constructor for a Model with a vertexCount.
	 *
	 * @param vaoID integer Unique ID of a Vertex Array Object, this is a memory reference for the Vertex Array Object (VAO).
	 * @param vertexCount integer Total amount of vertices.
	 */
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

    // Getters
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}



}