package main;

import AI.SimulateGenetic;
import AI.TestAI;
import GUI.GUIRenderer;
import GUI.GUITexture;
import Swing.ShotMenu;
import engine.graphics.Loader;
import engine.graphics.MasterRenderer;
import engine.graphics.models.RawModel;
import engine.graphics.models.TexturedModel;
import engine.graphics.textures.ModelTexture;
import engine.graphics.textures.TerrainTexture;
import engine.graphics.textures.TerrainTexturePack;
import engine.graphics.textures.objConverter.ModelData;
import engine.graphics.textures.objConverter.OBJFileLoader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import javafx.application.Application;
import maths.MousePicker;
import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.io.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import org.lwjglx.util.vector.Vector4f;
import physics.*;
import terrain.Terrain;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main implements Runnable {

	public Thread game;
	public Window window;
	public static final int WIDTH = 1600, HEIGHT = 900;
	public int i = 0;

	public Loader loader = new Loader();

	public RawModel modelTree;
	public RawModel modelGrass;
	public RawModel golfballModel;
	public RawModel stumpModel;
	public RawModel poleModel;

	public ModelData modelDataTree;
	public ModelData modelDataGrass;
	public ModelData modelDataGolfBall;
	public ModelData modelDataStump;
	public ModelData modelDataPole;

	public ModelTexture textureTree;
	public ModelTexture textureGrass;
	public ModelTexture textureStump;
	public ModelTexture fernTextureAtlas;
	public ModelTexture textureGolfBall;
	public ModelTexture texturePole;

	public TexturedModel texturedModelTree;
	public TexturedModel texturedModelGrass;
	public TexturedModel texturedModelFern;
	public TexturedModel texturedModelGolfBall;
	public TexturedModel texturedModelPole;
	public TexturedModel texturedModelStump;

	public MasterRenderer renderer;

	public Player player;

	public Camera camera;
	public List<Light> lights;

	public List<Entity> tree;
	public List<Entity> stump;
	public List<Entity> grass;
	public List<Entity> fern;

	public List<Entity> entities;
	public Entity pole;

	public Random random;

	public static Terrain terrain;


	public TerrainTexture backgroundTexture;
	public TerrainTexture rTexture;
	public TerrainTexture gTexture;
	public TerrainTexture bTexture;
	public TerrainTexturePack texturePack;
	public TerrainTexture blendMap;

	public List<GUITexture> guis;
	public GUITexture gui;
	public GUIRenderer guiRenderer;

	public WaterShader waterShader;
	public WaterRenderer waterRenderer;
	public List<WaterTile> waters;
	public WaterTile water;
	WaterFrameBuffers fbos;

	public static Vector2d shots;
	public static int shotCount = 0;
	private boolean win = false;
	private boolean terrainMethod;

	// background colour
	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;

	public long timeCurrent;
	public long timeLast = 0;

	public Vector2d playerStartPosition;
	public Vector2d goalPosition;
	public static boolean takingShot = false;
	public static boolean openNewWindow = true;
	public static boolean aiRunning = false;

	public void start() {
		game = new Thread(this, "game");
		game.start();
	}

	// create window with terrain
	public void init() throws Exception {
		window = new Window(WIDTH, HEIGHT, "Game");
		window.setBackgroundColor(RED,GREEN,BLUE);
		window.create();
		renderer = new MasterRenderer(loader);


		// load objects to RawModels
		modelDataTree = OBJFileLoader.loadOBJ("tree");
		modelDataGrass = OBJFileLoader.loadOBJ("grassModel");
		modelDataGolfBall = OBJFileLoader.loadOBJ("golf_ball3");
		modelDataStump = OBJFileLoader.loadOBJ("treeStump2");
		modelDataPole = OBJFileLoader.loadOBJ("pole");

		modelTree = loader.loadToVAO(modelDataTree.getVertices(), modelDataTree.getTextureCoords(),modelDataTree.getNormals(),modelDataTree.getIndices());
		modelGrass = loader.loadToVAO(modelDataGrass.getVertices(), modelDataGrass.getTextureCoords(),modelDataGrass.getNormals(),modelDataGrass.getIndices());
		golfballModel = loader.loadToVAO(modelDataGolfBall.getVertices(),modelDataGolfBall.getTextureCoords(),modelDataGolfBall.getNormals(),modelDataGolfBall.getIndices());
		stumpModel = loader.loadToVAO(modelDataStump.getVertices(),modelDataStump.getTextureCoords(),modelDataStump.getNormals(),modelDataStump.getIndices());
		poleModel = loader.loadToVAO(modelDataPole.getVertices(),modelDataPole.getTextureCoords(),modelDataPole.getNormals(),modelDataPole.getIndices());

		// create texture for terrain
		backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		bTexture = new TerrainTexture(loader.loadTexture("mud"));

		texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// create texture models
		fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		texturedModelTree = new TexturedModel(modelTree, new ModelTexture(loader.loadTexture("tree")));
		texturedModelGrass = new TexturedModel(modelGrass, new ModelTexture(loader.loadTexture("grassTexture")));
		texturedModelGolfBall = new TexturedModel(golfballModel, new ModelTexture(loader.loadTexture("golf4")));
		//texturedModelFern = new TexturedModel(fernModel, fernTextureAtlas);
		texturedModelStump = new TexturedModel(stumpModel, new ModelTexture(loader.loadTexture("wallTexture")));
		texturedModelPole = new TexturedModel(poleModel, new ModelTexture(loader.loadTexture("playerTexture")));

		texturedModelGrass.getTexture().setHasTransparency(true);
		texturedModelGrass.getTexture().setUseFakeLighting(true);


		// set shine and reflectivity of each textured model

		textureTree = texturedModelTree.getTexture();
		textureTree.setShineDamper(10);
		textureTree.setReflectivity(1);

		textureStump = texturedModelStump.getTexture();
		textureStump.setShineDamper(10);
		textureStump.setReflectivity(1);

		textureGrass = texturedModelGrass.getTexture();
		textureGrass.setShineDamper(10);
		textureGrass.setReflectivity(1);

		textureGolfBall = texturedModelGolfBall.getTexture();
		textureGolfBall.setShineDamper(10);
		textureGolfBall.setReflectivity(1);

		texturePole = texturedModelPole.getTexture();
		texturePole.setShineDamper(10);
		texturePole.setReflectivity(1);

		// generate terrain
		terrain = new Terrain(0,0,loader,texturePack, blendMap);


		// generate light
		lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(200,200,0), new Vector3f(1f,1f,1f)));


		// create and render small GUI in top right position if your ball landed in the hole
		guis = new ArrayList<>();
		gui = new GUITexture(loader.loadTexture("Win"), new Vector2f(0.75f, 0.75f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		guiRenderer = new GUIRenderer(loader);

		// create tree entity list
		tree = new ArrayList<Entity>();
		for(int i=0;i<SimulateMain.simulator.get_course().getTreeList().size();i++){
			float x = (float) SimulateMain.simulator.get_course().getTreeList().get(i).getP().get_x();
			float z = (float) SimulateMain.simulator.get_course().getTreeList().get(i).getP().get_y();
			float y = terrain.getHeightOfTerrain(x,z);
			tree.add(new Entity(texturedModelTree, new Vector3f(x,y,z),0,0,0,5));
		}


		// create grass entity list
		grass = new ArrayList<Entity>();
		random = new Random();
		for(int i=0;i<500;i++){
			float x = random.nextFloat()*800;
			float z = random.nextFloat()*800;
			float y = terrain.getHeightOfTerrain(x,z);
			grass.add(new Entity(texturedModelGrass, new Vector3f(x,y,z),0,0,0,1));
		}

		// create fern entity list
		stump = new ArrayList<Entity>();
		random = new Random();
		for(int i=0;i<50;i++){
			float x = random.nextFloat()*Terrain.SIZE;
			float z = random.nextFloat()*Terrain.SIZE;
			float y = terrain.getHeightOfTerrain(x,z);
			stump.add(new Entity(texturedModelStump, random.nextInt(4), new Vector3f(x,y,z),0,0,0,1));
		}


		// create water
		fbos = new WaterFrameBuffers();
		waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
		waters = new ArrayList<WaterTile>();
		water = new WaterTile(Terrain.SIZE/2,Terrain.SIZE/2,0);
		waters.add(water);

		playerStartPosition = SimulateMain.getStart();
		goalPosition = SimulateMain.getFlag();

		// generate ball
		player = new Player(texturedModelGolfBall, new Vector3f((float)playerStartPosition.get_x(),terrain.getHeightOfTerrain((float)(playerStartPosition.get_x()), (float)playerStartPosition.get_y()),(float) playerStartPosition.get_y()),0,0,0,10);

		pole = new Entity(texturedModelPole, new Vector3f((float)goalPosition.get_x(),terrain.getHeightOfTerrain(((float) goalPosition.get_x()),(float) goalPosition.get_y()),(float)goalPosition.get_y()-0.25f),0,0,0,10);

		// put the camera on the ball
		camera = new Camera(player);

	}

	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// render the scene continuously with updated scene
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
			// check if shot landed in hole
			if(SimulateMain.simulator.calcWin(SimulateMain.simulator.get_ball_position(),SimulateMain.getFlag(),1)&& !win && !takingShot){
				System.out.println();
				System.out.println("=!= Y O U   W I N =!=");
				System.out.println();

				win = true;
			}
		}

		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		window.destroy();
	}

	private void update() {
		window.update();
	}

	private void render() {
		camera.move();


		// if s is pressed on keyboard, get information for new shot
		if(Input.isKeyDown(GLFW.GLFW_KEY_S) && !takingShot && openNewWindow) {
			// if manual input was chosen, create GUI to ask for velocity of shot
			if (SimulateMain.getVersion() == 1) {
				ShotMenu.create();
				openNewWindow = false;
			} else {
				// if file input was chosen, get shot information from file and send information to physics engine
				if (shotCount < FileReader.getVelocity().length) {
					shots = FileReader.getShot(shotCount);
					SimulateMain.simulator.take_shot(shots, false);
					takingShot = true;
					shotCount++;
				} else {
					System.out.println("All shots have been made.");
				}
			}
		}

		if(Input.isKeyDown(GLFW.GLFW_KEY_A) && !takingShot && openNewWindow){
			openNewWindow = false;
			TestAI.runMazeAI(SimulateMain.getStart(),SimulateMain.getFlag());
		}

		/* if shot is taken, position of ball gets updated with received new position from physics engine,
			creating a rolling motion*/
		if (takingShot) {
			timeCurrent = System.currentTimeMillis();
			timeLast = timeCurrent;
			Vector2d newPosition = SimulateMain.simulator.act_timestep_from_distance();
			player.move(terrain, new Vector2f((float) newPosition.get_x(), (float) newPosition.get_y()));

			if(Tools.advRound(SimulateMain.simulator.get_engine().get_v().get_x(),0) == 0 && Tools.advRound(SimulateMain.simulator.get_engine().get_v().get_y(),0) == 0){
				takingShot = false;
				if(aiRunning && (TestAI.shotCount < TestAI.getBotShots().size())){
					TestAI.takeAIShot();
				}
			}
		}

		i++;




		renderer.processEntity(pole);

		GL11.glEnable(GL46.GL_CLIP_DISTANCE0);

        fbos.bindReflectionFrameBuffer();
        float distance = 2 * (camera.getPosition().y - water.getHeight());
        camera.getPosition().y -= distance;
        camera.invertPitch();

        // render scene from different angles/ distant
		renderer.processEntity(player);
		renderer.processTerrain(terrain);
		renderer.processEntity(pole);

		renderer.render(lights,camera, new Vector4f(0,-1,0, -water.getHeight()));

		for(int i = 0; i<tree.size(); i++){
			renderer.processEntity(tree.get(i));
		}
		/*
		for(int i = 0; i<stump.size(); i++){
			renderer.processEntity(stump.get(i));
		}

		 */

		camera.getPosition().y += distance;
		camera.invertPitch();

		fbos.bindRefractionFrameBuffer();

		renderer.processEntity(player);
		renderer.processTerrain(terrain);
		renderer.processEntity(pole);
		renderer.render(lights,camera, new Vector4f(0,-1,0, water.getHeight()));

		for(int i = 0; i<tree.size(); i++){
			renderer.processEntity(tree.get(i));
		}
		/*
		for(int i = 0; i<stump.size(); i++){
			renderer.processEntity(stump.get(i));
		}

		 */

		GL46.glDisable(GL46.GL_CLIP_DISTANCE0);
		fbos.unbindCurrentFrameBuffer();


		renderer.processEntity(player);
		renderer.processTerrain(terrain);
		renderer.processEntity(pole);
		renderer.render(lights,camera, new Vector4f(0,-1,0,100000));

		for(int i = 0; i<tree.size(); i++){
			renderer.processEntity(tree.get(i));
		}
		/*
		for(int i = 0; i<stump.size(); i++){
			renderer.processEntity(stump.get(i));
		}

		 */

		// only render small gui if you've won
		if(win){
			guiRenderer.render(guis);
		}

		waterRenderer.render(waters, camera);
		window.swapBuffers();

	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}



}
