public abstract class RailroadCar {
    private static int lastId;
    private int id;
    private double netWeight;
    private double grossWeight;
    public int getLastId(){
        return lastId;
    }
    public static void setLastId(int id){
        RailroadCar.lastId = id;
    }
}
