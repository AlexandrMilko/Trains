public class ThreadedSetOff extends Thread{
    private TrainSet t;

    public ThreadedSetOff(TrainSet t){
        this.t = t;
    }

    public void run(){
        synchronized(t) {
            t.setOff();
        }
    }
}
