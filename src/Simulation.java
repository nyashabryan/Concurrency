import java.lang.Thread;

public class Simulation extends Thread{

    Tree[] trees;

    public Simulation(Tree[] trees){
        this.trees = trees;        
    }

    @Override
    public void run(){
        try{
            Thread.sleep(2000);    
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        
    }
}