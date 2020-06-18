package water;

public class WaterTile {
	
	public static final float TILE_SIZE = 200;
	
	private float height;
	private float x,z;

// Constructor of Water Tile
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
