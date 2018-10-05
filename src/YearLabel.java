import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;

public class YearLabel extends JLabel implements Runnable{

    @Override
    public void run(){
        
        while(true){
            setText("Year: " + String.valueOf(TreeGrow.sundata.sunmap.yearNumber.get()));
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}