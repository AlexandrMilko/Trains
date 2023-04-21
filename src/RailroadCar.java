import java.util.ArrayList;
public abstract class RailroadCar {

    private static int lastId = 0;
    private int id;
    double netWeight;
    double grossWeight;
    public RailroadCar(){
        this.id = lastId++;
    }

    public static ArrayList<RailroadCar> generateRandomSet(int number){
        ArrayList<RailroadCar> rCars = new ArrayList<>();

        RailroadCar rCar;
        for(int i=0; i<number; ++i){
            RailroadCars rCarChoice = RailroadCars.pickRandomCar();
            switch(rCarChoice){
                case BasicRailroadFreightCar -> {
                    LightGoods goods = LightGoods.pickRandomGoods();
                    rCar = new BasicRailroadFreightCar(goods);
                    rCars.add(rCar);
                }
                case HeavyRailroadFreightCar -> {
                    HeavyGoods goods = HeavyGoods.pickRandomGoods();
                    rCar = new HeavyRailroadFreightCar(goods);
                    rCars.add(rCar);
                }
                case PassengerRailroadCar -> {
                    Passengers passengers = Passengers.pickRandomPassengers();
                    rCar = new PassengerRailroadCar(passengers);
                    rCars.add(rCar);
                }
            }
        }

        return rCars;
    }

    public abstract String toString();
    public abstract Enum<?> getGoods();
}
