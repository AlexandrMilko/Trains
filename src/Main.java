import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        RandomGraph<Station> map = new RandomGraph<>(100, "Station");
        System.out.println(map);

        ArrayList<Locomotive> L = Locomotive.generateRandomSet(5, "Bullet Train", map.getStations(), map);
        Thread[] threads = new Thread[L.size()];

    }
    private static void clear() throws IOException, InterruptedException{
        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
    }
}
