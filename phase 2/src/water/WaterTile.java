package water;

import terrain.Terrain;

public class WaterTile {
	
	public static final float TILE_SIZE = Terrain.SIZE/2;
	
	private float height;
	private float x,z;

	/**
	 * Constructor for the waterTiles.
	 * @param centerX float, center position of the x-coordinate.
	 * @param centerZ float, center position of the y-coordinate.
	 * @param height float, height position of the waterTile.
	 */
	public WaterTile(float centerX, float centerZ, float height){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}
// getters
	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}


}
