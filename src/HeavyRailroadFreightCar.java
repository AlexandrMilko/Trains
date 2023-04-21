public class HeavyRailroadFreightCar extends RailroadCar {

    private int id;
    private static int lastId;
    private final HeavyGoods goods;

    public HeavyRailroadFreightCar(HeavyGoods goods){
        super();
        this.goods = goods;
        switch (this.goods) {
            case IRON -> netWeight = 30_000;
            case GOLD -> netWeight = 25_000;
            default -> netWeight = 0;
        }
        grossWeight = netWeight + 25_000;
    }
    public HeavyRailroadFreightCar(){
        goods = null;
        netWeight = 0;
        grossWeight = netWeight + 25_000;
    }
    public String toString(){
        return "HeavyRailroadFreightCar" + id + " | "+ grossWeight + "kg";
    }

    public HeavyGoods getGoods(){
        return goods;
    }
}
