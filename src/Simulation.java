import java.lang.Thread;

public class Simulation extends Thread{

    Tree[][] trees;
    Land sunmap;

    public Simulation(Tree[][] trees, Land sunmap){
        this.trees = trees;        
        this.sunmap = sunmap;
    }

    @Override
    public void run(){

        while(true){    
            try{
                for(int i = 9; i >= 0; i--){
                    for (Tree tree: trees[i]){
                        tree.simulate(sunmap);
                    }
                }
                sunmap.yearNumber.incrementAndGet();
                sunmap.resetSunlight();
                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                for(int i = 0; i < trees.length; i++)
                for (Tree tree: trees[i]){
                    tree.setExt(0.6f);
                }
                // Reset the grid
                sunmap.resetSunlight();
            }
        }
    }
}