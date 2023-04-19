public class BasicRailroadFreightCar extends RailroadCar {

    private int id;
    private static int lastId;
    private final double netWeight;
    private final double grossWeight;
    private final LightGoods goods;

    public BasicRailroadFreightCar(LightGoods goods){
        super();
        this.goods = goods;
        switch (this.goods) {
            case SAND -> netWeight = 10_500;
            case COAL -> netWeight = 15_000;
            case WOOD -> netWeight = 8_000;
            default -> netWeight = 0;
        }
        grossWeight = netWeight + 25_000;
    }
    public BasicRailroadFreightCar(){
        goods = null;
        netWeight = 0;
        grossWeight = netWeight + 25_000;
    }
    public String toString(){
        return "RCar" + id + " | "+ grossWeight + "kg";
    }

    public LightGoods getGoods(){
        return goods;
    }
}
