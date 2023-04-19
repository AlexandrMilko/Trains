import java.io.IOException;
import java.util.ArrayList;

public class ThreadedOutput extends Thread{

    ArrayList<TrainSet> trains;

    public ThreadedOutput(ArrayList<TrainSet> trains){
        this.trains = trains;
    }

    @Override
    public void run(){
        synchronized (trains){
            try {
                while (true) {
                    TrainSet.outputInfo(trains);
                }
            } catch (IOException e) {
                System.err.println(e);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }
}
