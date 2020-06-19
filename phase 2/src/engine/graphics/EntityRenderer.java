package engine.graphics;


import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import entities.Entity;
import maths.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL46;
import org.lwjglx.util.vector.Matrix4f;
import shaders.StaticShader;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

	private StaticShader shader;
// Constructor for Entity

	/**
	 * Constructor for the EntityRenderer
	 * This is the renderer for an entity
	 * @param shader StaticShader the shader of the entity Renderer
	 * @param projectionMatrix Matrix4f Projection Matrix of the entity renderer
	 */
	public EntityRenderer(@NotNull StaticShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

// Render method

	/**
	 * render method, this renders all the entities
	 * @param entities Map<TexturedModel, List<Entity>
	 */
	public void render(@NotNull Map<TexturedModel, List<Entity>> entities){
		for(TexturedModel model:entities.keySet()){
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}


	/**
	 * This prepares the texturedModel for rendering.
	 * @param model1 TexturedModel
	 */
	public void prepareTexturedModel (@NotNull TexturedModel model1){

		RawModel model = model1.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model1.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if (texture.isHasTransparency()){
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(),texture.getReflectivity());
		GL46.glActiveTexture(GL46.GL_TEXTURE0);
		GL46.glBindTexture(GL46.GL_TEXTURE_2D, model1.getTexture().getID());

	}
// unbinding of a textured model to a vertex

	private void unbindTexturedModel(){

		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);

	}

	/**
	 * Preparation of entity
	 * @param entity Entity this is an entity that needs to be prepared for rendering, making of a transformation Matrix and loading it to the shader.
	 */
	private void prepareInstance(@NotNull Entity entity){

		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(entity.getTextureXOffset(),entity.getTextureYOffset());

	}

}