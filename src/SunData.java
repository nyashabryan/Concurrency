import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Arrays;

/**
 * The SunData Class describes the landscape as a Land object
 * and an Array of Trees.
 */
public class SunData{
	
	Land sunmap; // regular grid with average daily sunlight stored at each grid point
	Tree [][] trees; // array of arrays of individual tress located on the sunmap
	public static final int numberOfLayers = 10;
	public static final int layerSize = 2;
	int numberOfTrees = 0;
	/**
	 * Takes in Landscape data from a file and loads it into a Land object
	 * and an Array of Trees.
	 * @param fileName From which the data is being loaded.
	 */
	public void readData(String fileName){ 
		try{
			BufferedReader reader =  new BufferedReader(new FileReader(fileName));
			
			// load sunmap
			String[] line = reader.readLine().split(" ");
			int dimx = (int) Integer.parseInt(line[0]);
			int dimy = (int) Integer.parseInt(line[1]);
			line = reader.readLine().split(" ");
			sunmap = new Land(dimx,dimy);
			for(int x = 0; x < dimx; x++)
				for(int y = 0; y < dimy; y++) {
					sunmap.setInitSun(x,y,Float.parseFloat(line[(x)*dimy + y]));	
				}
			sunmap.resetSunlight();
			
			// load forest
			int numt = (int) Integer.parseInt(reader.readLine());
			numberOfTrees = numt;
			ArrayList<ArrayList<Tree>> listOfTrees = new ArrayList<ArrayList<Tree>>(numberOfLayers);
			for(int i = 0; i < numberOfLayers; i++){
				listOfTrees.add(new ArrayList<Tree>());
			}
			System.out.println(numt);
			for(int t=0; t < numt; t++)
			{
				line = reader.readLine().trim().split(" ");
				int layerIndex = (int)Float.parseFloat(line[2])/layerSize;
				List<Tree> aList = listOfTrees.get(layerIndex);
				aList.add(Tree.fromArray(line));

			}

			trees = new Tree[][]{

				Arrays.copyOf(listOfTrees.get(0).toArray(),listOfTrees.get(0).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(1).toArray(),listOfTrees.get(1).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(2).toArray(),listOfTrees.get(2).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(3).toArray(),listOfTrees.get(3).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(4).toArray(),listOfTrees.get(4).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(5).toArray(),listOfTrees.get(5).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(6).toArray(),listOfTrees.get(6).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(7).toArray(),listOfTrees.get(7).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(8).toArray(),listOfTrees.get(8).size(), Tree[].class),
				Arrays.copyOf(listOfTrees.get(9).toArray(),listOfTrees.get(9).size(), Tree[].class),
			};
			reader.close();
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
			System.exit(0);
		}
		catch (java.util.InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Writes the output data to a given fileName.
	 * @param fileName of the file where output will be written. 
	 */
	public void writeData(String fileName){
		try{ 
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.printf("%d\n", trees.length);
			for(int t=0; t < numberOfLayers; t++)
				for(int s = 0; s < trees[t].length; s++)
				printWriter.printf("%d %d %f\n", trees[t][s].getX(), trees[t][s].getY(), trees[t][s].getExtent());
			printWriter.close();
		 }
		 catch (IOException e){
			 System.out.println("Unable to open output file "+fileName);
				e.printStackTrace();
		 }
	}
}