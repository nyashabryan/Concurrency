import java.awt.Color;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * Custom JPanel class to represent the Forest rendering in the GUI.
 */
public class ForestPanel extends JPanel implements Runnable {
	Tree[][] forest;	// trees to render
	List<Integer> rndorder; // permutation of tree indices so that rendering is less structured
	static final ForkJoinPool pool = new ForkJoinPool();
	ForestPanel(Tree[][] trees) {
		forest=trees;
	}

	/**
	 * Overriden paintComponent method of the JPanel object inhereted by the 
	 * Forest panel object. Handles how the ForestPanel object is rendered.
	 */
	@Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		    
		long init = System.currentTimeMillis();
		// Use a divide and conquer algorithm to solve this problem
		for(int i =0; i < forest.length; i++){
			pool.invoke(new RenderParallel(g, forest[i], 0, forest[i].length, width, height));
		}
		System.out.println(System.currentTimeMillis() - init);
	}
	
	/**
	 * Implementation of the run method of the Forest Panel class. Overidden from the Runnable interface it 
	 * implements. 
	 */
	public void run() {
			
		// reordering so that trees are rendered in a more random fashion
		rndorder = new ArrayList<Integer>();
		for(int l = 0; l < forest.length; l++)
			rndorder.add(l);
		java.util.Collections.shuffle(rndorder);
		
		// render loop
		while(true) {
			repaint();
			try {
				Thread.sleep(20); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
	}

	/**
	 * Recursive action class responsible for rendering the trees in the 
	 * GUI.
	 */
	class RenderParallel extends RecursiveAction{

		Graphics g;
		Tree[] trees;
		int high;
		int low;
		int height;
		int width;

		/**
		 * Default Constructor for the RenderParallel Class
		 * @param g The Graphics object which is being rendered on.
		 * @param trees The array of trees in the extent class being rendered. 
		 * @param low The boundary of the section of the trees array the object is responsible
		 * 			for.
		 * @param high The boundary of the section of the trees array the object is responsible
		 * 			for.
		 * @param width The width of the Forest Panel object.
		 * @param height The height of the Forest Panel object.
		 */
		RenderParallel(Graphics g, Tree[] trees, int low, int high, int width, int height){
			this.g = g;
			this.trees = trees;
			this.low = low;
			this.high = high;
			this.width = width;
			this.height = height;
		}

		/**
		 * Overriden compute method of the RenderParallel class from the Recursive Action class.
		 * Handles the computation of the RenderParallel object.
		 */
		@Override
		protected void compute(){
			if(high -low > (int)trees.length){
				RenderParallel left = new RenderParallel(g, trees, low, (int)high/2, width, height);
				RenderParallel right = new RenderParallel(g, trees, (int)high/2, high, width, height);
				right.fork();
				left.compute();
				right.join();

			}else{
				ArrayList<Integer> pickingOrder = new ArrayList<Integer>();
				for(int i = low; i < high; i++)
					pickingOrder.add(i);
				Collections.shuffle(pickingOrder);
				for(int rt: pickingOrder){
					g.setColor(trees[rt].color);
					int X = trees[rt].getX();
					int Y = trees[rt].getY();
					int extent = (int)trees[rt].getExtent();
					int y1 = Y - extent;
					int x1 = X - extent;
					int x2 = X + extent;
					int y2 = Y + extent;
					if (y1 < 0)

						y1 = 0;
					if (x1 < 0)
						x1 = 0;
					if (x2 > width)
						x2 = width;
					if (y2 > height)
						y2 = height;
					g.fillRect(x1, y1, x2 - x1, y2 -y1);
				}
			}
		}
	}

}