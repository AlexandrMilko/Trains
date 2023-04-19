public enum RailroadCars {
    //TODO add all the rCars classes
    BasicRailroadFreightCar;
    public static RailroadCars pickRandomCar(){
        int carChoice = (int)Math.round(Math.random()*RailroadCars.values().length);
        switch(carChoice){
            case 0  -> { return BasicRailroadFreightCar; }
            default -> { return BasicRailroadFreightCar; }
        }
    }
}
