import java.lang.Math;
import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
public class Locomotive{
    private String name;
    private int id;
    private static int lastId = 0;
    private final int maxRailroadCars = 10;
    private final double maxWeight = 6000;
    private final int maxECars = 3; // maximum quantity of electricity cars that can be connected
    private double speed = 1000;

    public Locomotive(String name){
        this.name = name;
        this.id = lastId ++;
    }

    public void randomlyChangeSpeed(){
        //if the random value is more than 0.5, we add the speed. Otherwise, we will decrease it.
        double rand = Math.random();
        if (rand > 0.5) speed = speed*(1 + 0.03);
        else            speed = speed*(1 - 0.03);
    }
    public void randomlyChangeSpeed(double percentage){

        //If user enters more than 1, it means that they perhaps meant percent value and not proportion
        if(percentage >= 1) percentage /= 100.;

        //if the random value is more than 0.5, we add the speed. Otherwise, we will decrease it.
        double rand = Math.random();
        if (rand > 0.5) speed = speed*(1 + percentage);
        else            speed = speed*(1 - percentage);
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
        return name + id + "(" + journey.getStart() + " -> " + journey.getDestination() + ")";
    }

    public static ArrayList<Locomotive> generateRandomSet(int number, String name, HashSet<Station> stations, Graph<Station> stationsLayout){
        ArrayList<Locomotive> locomotives = new ArrayList<>();
        for(int i=0; i<number; ++i){
            try {
                Locomotive l = new Locomotive(name, stations, stationsLayout);
                locomotives.add(l);
            }
            catch (EdgeNotFoundException e){
                System.err.println(e);
            }
        }
        return locomotives;
    }

    @Override
    public void run(){ //This run just "performs" one iteration of the Locomotive move
        try                                      {Thread.sleep(1000);}
        catch (java.lang.InterruptedException e) {System.err.println(e);}
        if(distanceGone < journey.getDistance())
            distanceGone += speed * (period / 1000.);
    }
    public double getDistanceGone(){return distanceGone;}
    public void setOff(){
        try {
            System.out.println(this);
            System.out.println(journey.getPath());
            System.out.println();
        }catch(EdgeNotFoundException e){
            System.err.println(e);
        }
    }
}
