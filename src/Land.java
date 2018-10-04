import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Land Class describe the terrain in the simulation. Contains
 * Arrays which describe the sunlight hours in the terrain per
 * grid point.
 */
public class Land{
	
	AtomicInteger yearNumber = new AtomicInteger(0);
	float[][] gridSunlightHours;
	float[][] initialGridSunlightHours;
	private int dx;
	private int dy;
	private final static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	/**
	 * Constructor for the Land object. Takes in the dimensions of the Land.
	 * 
	 * @param dx The x dimension of the terrain.
	 * @param dy The y dimension of the terrain.
	 */
	Land(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		this.gridSunlightHours = new float[dx][dy];
		this.initialGridSunlightHours = new float[dx][dy];
	}

	/**
	 * Get the length of the landscape in the x direction.
	 * 
	 * @return x dimension of the land.
	 */
	public int getDimX() {
		return this.dx;
	}
	
	/**
	 * Get the length of the landscape in the y direction.
	 * 
	 * @return y dimension of the land.
	 */
	public int getDimY() {
		return this.dy;
	}
	
	
	/**
	 * Resets the current grid sunlight hours.
	 */
	public void resetSunlight() {
		for (int i = 0; i < this.dx; i++){
			this.gridSunlightHours[i] = Arrays.copyOf(initialGridSunlightHours[i],
				this.initialGridSunlightHours.length	
			);
		}
	}

	/**
	 * Returns the sunlight value of individual grid points in the 
	 * initialGridSunlightHoursArray.
	 * 
	 * @param x The x position of the grid point.
	 * @param y The y position of the grid point.
	 * @return The initial sunlight value of a grid point.
	 */
	public float getInitSun(int x, int y) {
		return this.initialGridSunlightHours[x][y];
	}
	
	/**
	 * Sets the sunlght value of individual grids points in the 
	 * initialGridSunlightHours Array.
	 * 
	 * @param x The x position of the grid point.
	 * @param y The y position of the grid point.
	 * @param val The value of the initial sunight on  grid point. 
	 */
	public void setInitSun(int x, int y, float val) {
		this.initialGridSunlightHours[x][y] = val;
	}
	
	/**
	 * Gets the current value of sunlight at a grid point.
	 * 
	 * @param x The x position of the grid point. 
	 * @param y The y position of the grid point. 
	 * @return The current value of sunlight on that grid point.
	 */
	public float getSun(int x, int y) {
		return this.gridSunlightHours[x][y];
	}
	
	/**
	 * Sets the current value of the sunlight at a grid point.
	 * 
	 * @param x The x position of the grid point.
	 * @param y The y position of the grid point.
	 * @param val The current value of sunlight at that grid point. 
	 */
	public void setSun(int x, int y, float val){
		this.gridSunlightHours[x][y] = val;
	}
	
}