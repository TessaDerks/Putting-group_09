package engine.graphics.models;

import engine.graphics.textures.ModelTexture;

public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture texture;

// Constructor for a textured Model, takes in a rawModel and textures for a model.
    public TexturedModel(RawModel model, ModelTexture texture){
        this.rawModel = model;
        this.texture = texture;
    }

// Getters
    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
