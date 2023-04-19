import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class TrainSet {

    private boolean wasPrinted = false;

    //We use it to monitor where all the trains are on the map
    private static final ArrayList<TrainSet> allTrains = new ArrayList<>();
    private static final int outputUpdatePeriod = 3000;
    private static final int tickPeriod = 1000;
    private static final String outputFileName = "AppState.txt";
    private Locomotive locomotive;
    private ArrayList<RailroadCar> rCars;
    private String name;
    private int id;
    private static int lastId = 0;
    private Edge<Station> journey;
    private Graph<Station> stationsLayout;
    private Station lastStation;
    private boolean isWaiting;

    //Set journey on your own
    public TrainSet(String name, Edge<Station> journey, Graph<Station> stationsLayout){
        this.name = name;
        this.journey = journey;
        this.id = lastId ++;
        this.stationsLayout = stationsLayout;
        allTrains.add(this);
    }

    //Set journey randomly
    public TrainSet(String name, HashSet<Station> stations, Graph<Station> stationsLayout){
        this.name = name;
        this.stationsLayout = stationsLayout;
        Station start = pickRandomStation(stations);
        Station destination = pickRandomStation(stations);
        HashMap<Station, HashSet<Station>> adjacencyList = this.stationsLayout.getAdjacencyList();
        this.journey = new Edge<Station>(start, destination, adjacencyList);
        this.id = lastId ++;
        allTrains.add(this);
    }

    //TODO make a constructor with railroadcars and locomotive specified

    public void randomlyChangeSpeed(){
        locomotive.randomlyChangeSpeed();
    }
    public void randomlyChangeSpeed(double percentage){
        locomotive.randomlyChangeSpeed(percentage);
    }

    private Station pickRandomStation(HashSet<Station> stations){
        int size = stations.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Station s : stations)
        {
            if (i == item)
                return s;
            i++;
        }
        return null; // TODO remove null return
    }

    @Override
    public String toString(){
        return name + " " + id;
    }

    //IMPORTANT: TODO change locomotives name
    public static ArrayList<TrainSet> generateRandomSet(int number, int rCarsNum, String name, HashSet<Station> stations, Graph<Station> stationsLayout){
        ArrayList<TrainSet> trains = new ArrayList<>();
        for(int i=0; i<number; ++i){
            try {
                TrainSet train = new TrainSet(name, stations, stationsLayout);
                Locomotive l = new Locomotive(name); //TODO make random selection from all the Classes for Locomotive and RailroadCars
                ArrayList<RailroadCar> rCars = RailroadCar.generateRandomSet(rCarsNum);
                train.setLocomotive(l);
                train.setRailroadCars(rCars);
                trains.add(train);
            }
            catch (EdgeNotFoundException e){
                System.err.println(e);
            }
        }
        return trains;
    }

    public void setLocomotive(Locomotive l){this.locomotive = l;}
    public void setRailroadCars(ArrayList<RailroadCar> rCars){this.rCars = rCars;}

    public double getDistanceGone(){return locomotive.getDistanceGone();}

    public static ThreadedSetOff[] setOff(ArrayList<TrainSet> trains){
        ThreadedSetOff[] threads = new ThreadedSetOff[trains.size()];
        for(int i=0; i<threads.length; ++i){
            threads[i] = new ThreadedSetOff(trains.get(i));
            threads[i].start();
        }
        return threads;
    }

    public void setOff() throws InterruptedException, IOException {
        isWaiting = false;
        Station start = journey.getStart();
        Station destination = journey.getDestination();
        lastStation = start;
        int distanceGoneSinceLastStation;

        // Moving from start to the destination
        while(lastStation != destination){
            // Moving from one station to another
            distanceGoneSinceLastStation = 0;
            while(distanceGoneSinceLastStation < journey.getDistanceBetweenTwoStations()){
                Thread.sleep(tickPeriod);
                distanceGoneSinceLastStation += locomotive.getSpeed();
                locomotive.increaseDistanceGone();
            }
            //Check if no trains are on the next railroad
            //If some is found, we stay on the station, waiting till it goes past.
            //TODO make it a sep function, make it synchronized?
            if(!anyTrainsAhead()){
                lastStation = this.getNextStation();
            }
            else{
                System.out.println(this.toString() + " TRAIN AHEAD!");
                isWaiting = true;
                while(anyTrainsAhead()){
                    System.out.println(this.toString() + " WAITING");
                    Thread.sleep(1000);
                }
                isWaiting = false;
            }
        }
    }

    private Station getNextStation() throws NextStationNotFound{
        LinkedList<Station> path = journey.getPath();
        int lastIndex = path.indexOf(lastStation);
        if(lastIndex != path.size()-1){
            int nextIndex = lastIndex+1;
            return path.get(nextIndex);
        }
        throw new NextStationNotFound(this + "You are at the destination already.");
    }
    private boolean anyTrainsAhead(){
        synchronized (allTrains) {
            for (TrainSet t : allTrains) {
                if ((t.lastStation == this.getNextStation()) && (t.getNextStation() == this.lastStation) && !t.isWaiting) {
                    return true;
                }
            }
            return false;
        }
    }

    private void printInfo(){
        String[] strings = {"_________ ", "| ##### | ", "|_#####_| ", "  o   o   "};
        for(int i=0; i<strings.length; ++i) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < rCars.size(); ++j) {
                if(i==1){
                    sb.append(strings[i].replace("#####", String.format("%5s", rCars.get(j).getGoods().toString())));
                }
                else if(i==2) {
                    sb.append(strings[i].replace("#####", String.format("%5s", rCars.get(j).getClass().toString().replaceAll("\\P{Lu}", ""))));
                }
                else sb.append(strings[i]);
            }
            strings[i] = sb.toString();
        }
        strings[0] = String.format("%17s", " ") + strings[0] + "____";
        try {
            strings[1] = String.format("%17s", lastStation) + strings[1] + "|DD|____T_" + "    " + this.getNextStation();
        }catch (NextStationNotFound e){
            strings[1] = String.format("%17s", lastStation) + strings[1] + "|DD|____T_" + "    " + "FINISH!!";
        }
        strings[2] = String.format("%17s", " ") + strings[2] + "|_ |_____|<";
        strings[3] = String.format("%17s", " ") + strings[3] + "  @-@-@-oo\\";
        System.out.printf("%17s", this);
        System.out.println();
        for(String s : strings) System.out.println(s);
        System.out.printf("Speed: %4f\n", locomotive.getSpeed());
        System.out.println("Path: " + journey.getPath());
        double proportionComplete = 100 * ((locomotive.getDistanceGone() / (journey.getPath().size()*journey.getDistanceBetweenTwoStations())));
        System.out.println("% of the whole Distance completed: " + proportionComplete);
        double proportionCompleteSinceLastStation = 100 * (locomotive.getDistanceGone() % journey.getDistanceBetweenTwoStations()) / journey.getDistanceBetweenTwoStations();
        System.out.println("% of Distance completed since last Station: " + proportionCompleteSinceLastStation);
        System.out.println();
        System.out.println();
    }
    public static void outputInfo(ArrayList<TrainSet> trains) throws IOException, InterruptedException {
        for (TrainSet t : trains) {
            if (!t.wasPrinted) {
                t.printInfo();
                t.wasPrinted = true;
            }
        }
        Thread.sleep(outputUpdatePeriod);
        if (wereAllPrinted(trains)) {
            Main.clearScreen();
            for (TrainSet t : trains) {
                t.wasPrinted = false;
            }
            saveInfoToFile(trains, outputFileName);
        }
    }
    private static boolean wereAllPrinted(ArrayList<TrainSet> trains){
        for (TrainSet t : allTrains) {
            if(!t.wasPrinted) return false;
        }
        return true;
    }
    private static void saveInfoToFile(ArrayList<TrainSet> trains, String filename){
        try(PrintStream ps = new PrintStream(new FileOutputStream(filename))){
            for(TrainSet t : trains){
                t.appendInfoToStream(ps);
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }
    public void appendInfoToStream(PrintStream ps){
        String[] strings = {"_________ ", "| ##### | ", "|_#####_| ", "  o   o   "};
        for(int i=0; i<strings.length; ++i) {
            StringBuilder sb = new StringBuilder();
            for (RailroadCar rCar : rCars) {
                if (i == 1) {
                    sb.append(strings[i].replace("#####", String.format("%5s", rCar.getGoods().toString())));
                } else if (i == 2) {
                    sb.append(strings[i].replace("#####", String.format("%5s", rCar.getClass().toString().replaceAll("\\P{Lu}", ""))));
                } else sb.append(strings[i]);
            }
            strings[i] = sb.toString();
        }
        strings[0] = String.format("%17s", " ") + strings[0] + "____";
        try {
            strings[1] = String.format("%17s", lastStation) + strings[1] + "|DD|____T_" + "    " + this.getNextStation();
        }catch (NextStationNotFound e){
            strings[1] = String.format("%17s", lastStation) + strings[1] + "|DD|____T_" + "    " + "FINISH!!";
        }
        strings[2] = String.format("%17s", " ") + strings[2] + "|_ |_____|<";
        strings[3] = String.format("%17s", " ") + strings[3] + "  @-@-@-oo\\";
        ps.printf("%17s", this);
        ps.println();
        for(String s : strings) ps.println(s);
        ps.printf("Speed: %4f\n", locomotive.getSpeed());
        ps.println("Path: " + journey.getPath());
        double proportionComplete = 100 * ((locomotive.getDistanceGone() / (journey.getPath().size()*journey.getDistanceBetweenTwoStations())));
        ps.println("% of the whole Distance completed: " + proportionComplete);
        double proportionCompleteSinceLastStation = 100 * (locomotive.getDistanceGone() % journey.getDistanceBetweenTwoStations()) / journey.getDistanceBetweenTwoStations();
        ps.println("% of Distance completed since last Station: " + proportionCompleteSinceLastStation);
        ps.println();
        ps.println();
    }
}
