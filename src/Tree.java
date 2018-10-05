import java.awt.Color;
import java.util.Random;
/**
 * The Tree class. Adapted from James Gain.
 */
public class Tree{
	
	private int xpos;	// x-coordinate of center of tree canopy
	private int ypos;	// y-coorindate of center of tree canopy
	private float ext;	// extent of canopy out in vertical and horizontal from center	
	public final static float growfactor = 100.0f; // divide average sun exposure by this amount to get growth in extent
	public final static float shadefactor = 10.0f; // divide the sun exposure for a cell by this when reducing sunlight.
	public final Color color;
	/**
	 * Constructor for the Tree class.
	 * @param x The x coordinate position for the tree center.
	 * @param y The y coordinate position for the tree center.
	 * @param e The extent of the Tree.
	 */
	public Tree(int x, int y, float e){
			this.xpos=x; this.ypos=y; this.ext=e;
			Random rnd = new Random();
			this.color = new Color(rnd.nextInt(100), 150+rnd.nextInt(100), rnd.nextInt(100));
	}

	/**
	 * Empty constructor for the Tree object. 
	 */
	public Tree(){
		this(0,0,0);
	}

	/**
	 * Static method to create a Tree object from an array of values.
	 * The Array is expected to be in the following format:
	 * arr[0] = x position.
	 * arr[1] = y position.
	 * arr[2] = float value of the extent of the tree.
	 * @param arr
	 * @return new Tree object.
	 */
	public static Tree fromArray(String[] arr){
		return new Tree(
			(int)Integer.parseInt(arr[0]),
			(int)Integer.parseInt(arr[1]),
			(float)Float.parseFloat(arr[2])
		);
	}
	
	/**
	 * Getter for the x position of the Tree Centre.
	 * @return Tree centre x position.
	 */
	synchronized public int getX() {
			return this.xpos;
	}
	
	/**
	 * Getter for the y position for the tree center.
	 * @return Tree centre y position.
	 */
	synchronized public int getY() {
		return this.ypos;
	}
	
	/**
	 * Returns the extent of a Tree Object.
	 * @return Tree extent.
	 */
	synchronized public float getExtent() {
		return this.ext;
	}
	
	/**
	 * Sets the extent of the Tree.
	 * 
	 * @param e The new extent of the Tree. 
	 */
	synchronized public void setExt(float e) {
			this.ext = e;
	}
	
	/**
	 * Grows the tree by increasing the tree's extent.
	 * 
	 * @param e The float value the tree is going to grow by.
	 */
	synchronized public void grow(float e){
		this.ext+= e;
	}
	
	/**
	 * Runs the tree level simulation for each of the trees.
	 * 
	 */
	synchronized public void simulate(Land sunmap){
		float averageSunHours = calculateAverageSun(sunmap);
		reduceHours(sunmap);
		this.grow(averageSunHours/growfactor);
	}

	/**
	 * Reduces the Sun hours on a area the tree is covering. 
	 * @param sunmap
	 */
	public void reduceHours(Land sunmap){
		for (int i = this.xpos - Math.round(this.ext); i < this.xpos + Math.round(this.ext) + 1; i++){
			for (int j = this.ypos - Math.round(this.ext); j < this.ypos + Math.round(this.ext) + 1; j++){
				if (i < 0 || j < 0 || i > sunmap.getDimX()-1 || j > sunmap.getDimY()-1)
					continue;
				sunmap.setSun(i, j, sunmap.getSun(i, j)/shadefactor); ;
			}
		}
	}

	/**
	 * Calcualate the average sunlight for each tree. 
	 * @param sunmap
	 * @return average sunlight of tree cells. 
	 */
	public float calculateAverageSun(Land sunmap){
		float total = 0;
		int extent = Math.round(this.getExtent());
		for (int i = this.xpos - extent; i < this.xpos + extent + 1; i++){
			for (int j = this.ypos - extent; j < this.ypos + extent + 1; j++){
				if (i < 0 || j < 0 || i > sunmap.getDimX()-1 || j > sunmap.getDimY()-1)
					continue;
				total += sunmap.getSun(i, j);
			}
		}
		if (Math.round(this.ext) == 0){
			return total;
		}
		return  total/((float)Math.pow(Math.round(this.ext),2));
	}
	/**
	 * Returns the Tree getGrowFactor
	 * 
	 * @return Tree growFactor
	 */
	public static float getGrowFactor(){
		return Tree.growfactor;
	}

	/**
	 * Resets the extent of a tree to a value of 0.4.
	 */
	synchronized public void resetExtent(){
		this.setExt(0.4f);
	}
}