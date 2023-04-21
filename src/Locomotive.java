import java.lang.Math;
public class Locomotive{
    private String name;
    private int id;
    private static int lastId = 0;
    private final int maxRailroadCars = 10;
    private final double maxWeight = 150_000;
    private final int maxECars = 3; // maximum quantity of electricity cars that can be connected
    private double speed = 199; //per second
    private double distanceGone;

    public Locomotive(String name){
        this.name = name;
        this.id = lastId ++;
    }

    public void randomlyChangeSpeed(){
        //if the random value is more than 0.5, we add the speed. Otherwise, we will decrease it.
        double rand = Math.random();
        if (rand > 0.5) speed = speed*(1 + 0.03);
        else            speed = speed*(1 - 0.03);
        if (speed < 0)  speed = 0;
    }
    public void randomlyChangeSpeed(double percentage){

        //If user enters more than 1, it means that they perhaps meant percent value and not proportion
        if(percentage >= 1) percentage /= 100.;

        //if the random value is more than 0.5, we add the speed. Otherwise, we will decrease it.
        double rand = Math.random();
        if (rand > 0.5) speed = speed*(1 + percentage);
        else            speed = speed*(1 - percentage);
        if (speed < 0)  speed = 0;
    }

    @Override
    public String toString(){ //TODO use StringBuilder?
        return "Locomotive: " + name + id;
    }
    public double getDistanceGone(){return distanceGone;}
    public void increaseDistanceGone(){distanceGone += speed;}
    public void increaseDistanceGone(double value){distanceGone += value;}
    public double getSpeed(){return speed;}
    public void clearDistance(){distanceGone = 0;}

    public int getMaxRailroadCars(){return maxRailroadCars;}
    public int getMaxECars(){return maxECars;}
    public double getMaxWeight(){return maxWeight;}
}
