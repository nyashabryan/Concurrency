/**
 * The Tree class. Adapted from James Gain.
 */
public class Tree{
	
	private int xpos;	// x-coordinate of center of tree canopy
	private int ypos;	// y-coorindate of center of tree canopy
	private float ext;	// extent of canopy out in vertical and horizontal from center
		
	public final static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
	/**
	 * Constructor for the Tree class.
	 * @param x The x coordinate position for the tree center.
	 * @param y The y coordinate position for the tree center.
	 * @param e The extent of the Tree.
	 */
	public Tree(int x, int y, float e){
			this.xpos=x; this.ypos=y; this.ext=e;
	}

	public Tree(){
		this(0,0,0);
	}
		
	public int getX() {
			return this.xpos;
	}
		
	public int getY() {
		return this.ypos;
	}
		
	public float getExtent() {
		return this.ext;
	}
		
	public void setExt(float e) {
			this.ext = e;
	}
	
	/**
	 * Returns the Tree getGrowFactor
	 * @return Tree growFactor
	 */
	public static float getGrowFactor(){
		return Tree.growfactor;
	}
}