import java.awt.Color;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;
import java.util.concurrent.ForkJoinPool;


public class ForestPanel extends JPanel implements Runnable {
	Tree[][] forest;	// trees to render
	List<Integer> rndorder; // permutation of tree indices so that rendering is less structured
	static final ForkJoinPool pool = new ForkJoinPool();
	ForestPanel(Tree[][] trees) {
		forest=trees;
	}

	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		    
		// draw the forest in different canopy passes
		Random rnd = new Random(0); // providing the same seed gives trees consistent colouring
		long init = System.currentTimeMillis();


		// Use a divide and conquer algorithm to solve this problem
		for(int i =0; i < forest.length; i++){
			pool.invoke(new RenderParallel(g, forest[i], 0, forest[i].length, width, height));
		}
		
		//System.out.println(System.currentTimeMillis() - init);
	}
	
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

	class RenderParallel extends RecursiveAction{

		Graphics g;
		Tree[] trees;
		int high;
		int low;
		int height;
		int width;

		RenderParallel(Graphics g, Tree[] trees, int low, int high, int width, int height){
			this.g = g;
			this.trees = trees;
			this.low = low;
			this.high = high;
			this.width = width;
			this.height = height;
		}

		@Override
		public void compute(){
			if(high -low > 20000){
				RenderParallel left = new RenderParallel(g, trees, low, (int)high/2, width, height);
				RenderParallel right = new RenderParallel(g, trees, (int)high/2, high, width, height);

				right.fork();
				left.compute();
				right.join();

			}else{
				// start from small trees of [0, 2) extent
				float minh = 0.0f;
				float maxh = 2.0f;

				for(int layer = 0; layer <= 10; layer++) {
					for(int rt = low; rt < high; rt++){
						if(trees[rt].getExtent() >= minh && trees[rt].getExtent() < maxh) { // only render trees in current band
							// draw trees as rectangles centered on getX, getY with random greenish colour
							g.setColor(trees[rt].color);
							int y1 = trees[rt].getY() - (int) trees[rt].getExtent();
							int x1 = trees[rt].getX() - (int) trees[rt].getExtent();
							int x2 = trees[rt].getX() + (int) trees[rt].getExtent();
							int y2 = trees[rt].getY() + (int) trees[rt].getExtent();
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
						// g.setColor(Color.black);
						// g.fillRect(forest[rt].getY(), forest[rt].getX(), 1, 1); // draw the trunk
					}
					minh = maxh;  // next band of trees
					maxh += 2.0f;
				}
			}
		}
	}

}