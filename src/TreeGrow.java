import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class TreeGrow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;
	static SunData sundata;
	

	/**
	 * Starts log measurement of time by storing the current value of
	 * time as a static long value. 
	 */
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculate the time elapsed since tick function was called as a
	 * long value in milliseconds.
	 * 
	 * @return Time elapsed.
	 */
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	/**
	 * Sets up the GUI of the application by creating a main frame(window) and
	 * creating an object of the Forest Panel class. 
	 * 
	 * @param frameX Dimension of the main frame in the x direction.
	 * @param frameY Dimension of the main frame in the x direction.
	 * @param trees The array of trees. 
	 */
	public static void setupGUI(int frameX,int frameY,Tree [][] trees) {
		Dimension fsize = new Dimension(600, 600);
		// Frame init and dimensions
    	JFrame mainFrame = new JFrame("Photosynthesis"); 
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.setPreferredSize(fsize);
    	mainFrame.setSize(600, 600);
		mainFrame.setLayout(new GridLayout(2,1));
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
		  g.setPreferredSize(new Dimension(500, 500));
		  
		// Define the buttons 
		JButton pauseButton = new JButton("PAUSE");
		pauseButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeGrow.pauseSimulation();
			}
		});

		JButton playButton = new JButton("PLAY");
		playButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeGrow.resumeSimulation();
			}
		});
		
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeGrow.resetSimulation();
			}
		});

		JButton endButton = new JButton("END");
		endButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeGrow.endSimulation();
			}
		});

		JPanel buttonFrame =  new JPanel();
		buttonFrame.add(endButton);
		buttonFrame.add(pauseButton);
		buttonFrame.add(playButton);
		buttonFrame.add(resetButton);
 
		//mainFrame.add(buttonFrame);
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);
    	
		// Add buttons to G
		/*
		g.add(endButton);
		g.add(pauseButton);
		g.add(playButton);
		g.add(resetButton);
    	*/
      	mainFrame.setLocationRelativeTo(null);  // Center window on screen.
      	mainFrame.add(g); //add contents to window
        mainFrame.setContentPane(g);     
        mainFrame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
	/**
	 * The main method of the application. This is the controller of the application. 
	 * Where the application is started.
	 * 
	 * @param args The fileName of the input data.
	 */		
	public static void main(String[] args) {
		sundata = new SunData();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
			System.exit(0);
		}
				
		// read in forest and landscape information from file supplied as argument
		sundata.readData(args[0]);
		System.out.println("Data loaded");
		
		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		System.out.println(frameX);
		System.out.println(frameY);
		setupGUI(frameX, frameY, sundata.trees);
		
		
		System.out.println(sundata.sunmap.initialGridSunlightHours);
		System.out.println(sundata.sunmap.gridSunlightHours);
		System.out.println(sundata.sunmap.getSun(1, 1));
		System.out.println(sundata.sunmap.getInitSun(1, 1));
		sundata.sunmap.setSun(1, 1, 12);
		System.out.println(sundata.sunmap.getSun(1, 1));
		System.out.println(sundata.sunmap.getInitSun(1, 1));

		// create and start simulation loop here as separate thread
		Land land = sundata.sunmap;
		System.out.println(land);
		Simulation simulation = new Simulation(sundata.trees, land);
		simulation.start();
	}


	public static void endSimulation(){
		System.exit(0);
	}

	public static void resetSimulation(){
		pauseSimulation();
		sundata.sunmap.resetSunlight();
		for (Tree[] trees: sundata.trees){
			for(Tree tree: trees){
				tree.resetExtent();
			}
		}
		resumeSimulation();
	}

	public static void resumeSimulation(){
		;
	}

	public static void pauseSimulation(){
		;
	}
}