package com.mygdx.puttingame;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

public class Entity {

    private Mesh mesh;
    private Vector3 position;

    public Entity(Mesh mesh){
        this.mesh = mesh;
        position = new Vector3(0f,0f,0f);
    }

    public void setPosition(float x, float y, float z){
        position.set(x,y,z);
    }

    public Vector3 getPosition(){
        return position;
    }

    public Mesh getMesh(){
        return mesh;
    }

}
