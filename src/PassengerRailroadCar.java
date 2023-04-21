public class PassengerRailroadCar extends RailroadCar implements ElectricalGrid{

    private int id;
    private static int lastId;
    private final Passengers passengers;
    private boolean connected;
    public PassengerRailroadCar(Passengers passengers){
        super();
        this.passengers = passengers;
        switch (this.passengers) {
            case PEOPLE -> netWeight = 2_000;
            case ELVES -> netWeight = 2_500;
            case ALIENS -> netWeight = 5_000;
            default -> netWeight = 0;
        }
        grossWeight = netWeight + 25_000;
    }
    public PassengerRailroadCar(){
        passengers = null;
        netWeight = 0;
        grossWeight = netWeight + 25_000;
    }
    public String toString(){
        return "PassengerRailroadCar" + id + " | "+ grossWeight + "kg";
    }

    public Passengers getGoods(){
        return passengers;
    }

    @Override
    public void connect(){
        connected = true;
    }
    @Override
    public void disconnect(){
        connected = true;
    }
}
