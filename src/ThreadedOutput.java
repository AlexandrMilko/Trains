import java.util.ArrayList;

public class ThreadedOutput extends Thread{

    ArrayList<TrainSet> trains;

    public ThreadedOutput(ArrayList<TrainSet> trains){
        this.trains = trains;
    }

    @Override
    public void run(){
        synchronized (trains){
            while (true) {
                try {
                    TrainSet.outputInfo(trains);
                } catch (Exception e) {
                    System.err.println(e);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
