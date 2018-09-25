import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * The SunData Class describes the landscape as a Land object
 * and an Array of Trees.
 */
public class SunData{
	
	Land sunmap; // regular grid with average daily sunlight stored at each grid point
	Tree [] trees; // array of individual tress located on the sunmap	
	
	/**
	 * Takes in Landscape data from a file and loads it into a Land object
	 * and an Array of Trees.
	 * @param fileName From which the data is being loaded.
	 */
	public void readData(String fileName){ 
		try{ 
			Scanner sc = new Scanner(new File(fileName));
			
			// load sunmap
			int dimx = sc.nextInt(); 
			int dimy = sc.nextInt();
			sunmap = new Land(dimx,dimy);
			for(int x = 0; x < dimx; x++)
				for(int y = 0; y < dimy; y++) {
					sunmap.setInitSun(x,y,sc.nextFloat());	
				}
			sunmap.resetSunlight();
			
			// load forest
			int numt = sc.nextInt();
			trees = new Tree[numt];
			for(int t=0; t < numt; t++)
			{
				int xloc = sc.nextInt();
				int yloc = sc.nextInt();
				float ext = (float) sc.nextInt();
				trees[t] = new Tree(xloc, yloc, ext);
			}
			sc.close(); 
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (java.util.InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
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
			 for(int t=0; t < trees.length; t++)
				 printWriter.printf("%d %d %f\n", trees[t].getX(), trees[t].getY(), trees[t].getExtent());
			 printWriter.close();
		 }
		 catch (IOException e){
			 System.out.println("Unable to open output file "+fileName);
				e.printStackTrace();
		 }
	}
	
}