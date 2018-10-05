import java.lang.Thread;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation extends Thread{

    Tree[][] trees;
    Land sunmap;
    AtomicBoolean running;
    ForkJoinPool pool = new ForkJoinPool();

    public Simulation(Tree[][] trees, Land sunmap, AtomicBoolean running){
        this.trees = trees;        
        this.sunmap = sunmap;
        this.running = running;
    }

    @Override
    public void run(){

        while(true){    
            try{
                while(!running.get()){}
                for(int i = 9; i >= 0; i--){
                    pool.invoke(new SimRunner(trees[i], 0, trees[i].length, sunmap));
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

    public class SimRunner extends RecursiveAction{

        Tree[] trees;
        int low;
        int high;
        Land sunmap;

        public SimRunner(Tree[] trees, int low, int high, Land sunmap){
            this.trees = trees;
            this.low = low;
            this.high = high;
            this.sunmap = sunmap;
        }

        @Override
        protected void compute(){
            if(this.high - this.low < (int)(this.trees.length/2)){
                for(int i = low; i < high; i++){
                    trees[i].simulate(sunmap);
                }
            }else{
                SimRunner left = new SimRunner(trees, this.low, (int)((this.low + this.high)/2), sunmap);
                SimRunner right = new SimRunner(trees, (int)((this.low + this.high)/2), this.high, sunmap);
                right.fork();
                left.compute();
                right.join();
            }
        }

    }
}