package com.mygdx.puttingame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.project.puttingsimulator.*;

public class What extends ApplicationAdapter {

    private List<Entity> entities;
    private List<Entity> golfBall;
    private List<Entity> northBall;
    private List<Entity> water;
    private List<Entity> put;
    private Matrix4 translationMatrix;
    private Camera cam;
    private Mesh mesh;
    public Mesh sphereMesh;
    private Mesh waterMesh;
    private Mesh putMesh;
    private Mesh northMesh;
    private ShaderProgram shader;
    private ShaderProgram shader2;
    private ShaderProgram shader3;
    private ShaderProgram shader4;
    private ShaderProgram shader5;
    private Vector3 lightDirection;
    private FirstPersonCameraController controller;
    public Function2d function;
    public static Vector2d flag;
    public static double flagDia;
    public static Vector2d ball;
    public static Entity ent;



    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        super.create();
        translationMatrix = new Matrix4();

        //fieldMesh
        MeshBuilder builder = new MeshBuilder();
        VertexAttributes attributes = new VertexAttributes(new VertexAttribute(VertexAttributes.Usage.Position, 3,"a_Position"));
        builder.begin(attributes, GL20.GL_TRIANGLES);
        builder.box(0.2f,0.2f,0.2f);
        mesh = builder.end();
        //waterMesh
        builder = new MeshBuilder();
        builder.begin(attributes,GL20.GL_TRIANGLES);
        builder.box(40.0f,0.1f,40.0f);
        waterMesh = builder.end();
        //golfBallMesh
        builder = new MeshBuilder();
        builder.begin(attributes,GL20.GL_TRIANGLES);
        builder.sphere(0.2f, 0.2f, 0.2f, 20, 20);
        sphereMesh = builder.end();
        //northBallMesh
        builder = new MeshBuilder();
        builder.begin(attributes,GL20.GL_TRIANGLES);
        builder.sphere(2.5f, 2.5f, 2.5f, 20, 20);
        northMesh = builder.end();
        //putMesh
        builder = new MeshBuilder();
        builder.begin(attributes,GL20.GL_TRIANGLES);
        builder.sphere((float)flagDia, (float)(20 * flagDia), (float)flagDia, 20, 20);
        putMesh = builder.end();

        // water creation
        water = new ArrayList<Entity>();
        int xMaxSize = 40;
        int yMaxSize = 40;

        Entity ent2 = new Entity(waterMesh);
        ent2.setPosition(0, (float) -0.1, 0);
        water.add(ent2);



        // golfBall creation
        golfBall = new ArrayList<Entity>();
        ent = new Entity(sphereMesh);
        if(SimulateMain.simulator.get_ball_position() != null){
            ball = SimulateMain.simulator.get_ball_position();
        }
        ent.setPosition((float)ball.get_x(),(float) (function.evaluate(ball)+0.3),(float)ball.get_y());
        golfBall.add(ent);

        // northBall creation
        northBall = new ArrayList<Entity>();
        Entity entN = new Entity(northMesh);
        entN.setPosition(-25.0f,0.0f,0.0f);
        golfBall.add(entN);


        // grassField creation
        entities = new ArrayList<Entity>();

        for (double i=-(0.5*yMaxSize);i<=(0.5*yMaxSize);i+= 0.2){
            for (double j=-(0.5*xMaxSize);j<=(0.5*xMaxSize);j+= 0.2) {

                Entity ent1 = new Entity(mesh);
                ent1.setPosition((float)-(i), (float) (function.evaluate(new Vector2d(j,i))-0.2), (float)-(j));
                entities.add(ent1);
            }
        }

        // Put creation
        put = new ArrayList<Entity>();
        Entity ent4 = new Entity(putMesh);
        ent4.setPosition((float)flag.get_x(), (float) function.evaluate(flag), (float)flag.get_y());
        put.add(ent4);


        shader = new ShaderProgram(Gdx.files.internal("vertexshader.glsl").readString(), Gdx.files.internal("fragmentshader.glsl").readString());
        shader2 = new ShaderProgram(Gdx.files.internal("vertexshader.glsl").readString(), Gdx.files.internal("fragmentshader2.glsl").readString());
        shader3 = new ShaderProgram(Gdx.files.internal("vertexshader.glsl").readString(), Gdx.files.internal("fragmentshader3.glsl").readString());
        shader4 = new ShaderProgram(Gdx.files.internal("vertexshader.glsl").readString(), Gdx.files.internal("fragmentshader4.glsl").readString());
        shader5 = new ShaderProgram(Gdx.files.internal("vertexshader.glsl").readString(), Gdx.files.internal("fragmentshader4.glsl").readString());


        cam = new PerspectiveCamera(60, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cam.position.set(0f,5f,0f);
        //cam.lookAt(0f,0f,0f);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();




        controller = new FirstPersonCameraController(cam);
        Gdx.input.setInputProcessor(controller);


    }
    public void render(){
        super.render();

        lightDirection = new Vector3(13f,16f, 10f);

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        controller.update(Gdx.graphics.getDeltaTime());
        cam.update(true);

        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT| GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(135f/255f,206f/255f, 235f/255f, 1f);

        // field Render
        shader.begin();
        shader.setUniformMatrix("viewProj", cam.combined);
        shader.setUniformf("lightDirection", lightDirection);

        for (Entity e : entities){

            translationMatrix.setToTranslation(e.getPosition());
            shader.setUniformMatrix("model", translationMatrix);
            e.getMesh().render(shader, GL20.GL_TRIANGLES);

        }
        shader.end();


        // golfball Render
        shader2.begin();
        shader2.setUniformMatrix("viewProj", cam.combined);
        shader2.setUniformf("lightDirection", lightDirection);

        for (Entity b : golfBall){

            translationMatrix.setToTranslation(b.getPosition());
            shader2.setUniformMatrix("model", translationMatrix);
            b.getMesh().render(shader2, GL20.GL_TRIANGLES);

        }
        shader2.end();

        // water Render
        shader3.begin();
        shader3.setUniformMatrix("viewProj", cam.combined);
        shader3.setUniformf("lightDirection", lightDirection);

        for (Entity e : water){

            translationMatrix.setToTranslation(e.getPosition());
            shader3.setUniformMatrix("model", translationMatrix);
            e.getMesh().render(shader3, GL20.GL_TRIANGLES);

        }

        shader3.end();


        shader4.begin();
        shader4.setUniformMatrix("viewProj", cam.combined);
        shader4.setUniformf("lightDirection", lightDirection);

        for (Entity b : put){

            translationMatrix.setToTranslation(b.getPosition());
            shader4.setUniformMatrix("model", translationMatrix);
            b.getMesh().render(shader4, GL20.GL_TRIANGLES);

        }
        shader4.end();

        shader5.begin();
        shader5.setUniformMatrix("viewProj", cam.combined);
        shader5.setUniformf("lightDirection", lightDirection);

        for (Entity n : northBall){

            translationMatrix.setToTranslation(n.getPosition());
            shader5.setUniformMatrix("model", translationMatrix);
            n.getMesh().render(shader5, GL20.GL_TRIANGLES);

        }
        shader5.end();



    }


}
