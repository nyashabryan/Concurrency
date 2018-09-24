public class Land{
	
	private float[][] gridSunlightHours;
	private int dx;
	private int dy;
	private final static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	Land(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		this.gridSunlightHours = new float[dx][dy];
	}

	public int getDimX() {
		return this.dx;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDimY() {
		return this.dy;
	}
	
	// Reset the shaded landscape to the same as the initial sun exposed landscape
	// Needs to be done after each growth pass of the simulator
	public void resetShade() {
		// to do
	}
	
	public float getFull(int x, int y) {
		// to do
		return 0.0f; // incorrect value
	}
	
	public void setFull(int x, int y, float val) {
		// to do 
	}
	
	public float getShade(int x, int y) {
		// to do 
		return 0.0f; // incorrect value
	}
	
	public void setShade(int x, int y, float val){
		// to do
	}
	
	// reduce the 
	public void shadow(Tree tree){
		// to do
	}
}