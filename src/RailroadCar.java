import java.util.ArrayList;
public abstract class RailroadCar {

    private static int lastId = 0;
    private int id;
    private double netWeight;
    private double grossWeight;
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
            }
        }

        return rCars;
    }

    public abstract String toString();
    public abstract Enum<?> getGoods();
}
