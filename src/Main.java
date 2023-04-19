import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        RandomGraph<Station> map = new RandomGraph<>(100, "Station");

        ArrayList<TrainSet> trains = TrainSet.generateRandomSet(5, 3, "Bullet Train", map.getStations(), map);
        ThreadedSetOff[] trainsThreads = TrainSet.setOff(trains);
        ThreadedOutput to = new ThreadedOutput(trains);
        to.start();

        for(int i=0; i<trainsThreads.length; ++i){
            trainsThreads[i].join();
        }


    }
    public static void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
    }
}
