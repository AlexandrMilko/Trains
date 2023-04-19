public class ThreadedSetOff extends Thread{
    private TrainSet t;

    public ThreadedSetOff(TrainSet t){
        this.t = t;
    }

    public void run(){
        synchronized(t) {
            try {
                t.setOff();
            } catch (java.lang.Exception e) {
                System.err.println(t.toString() + e);
            }
        }
    }
}
