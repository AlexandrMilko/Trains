public enum RailroadCars {
    //TODO add all the rCars classes
    BasicRailroadFreightCar, HeavyRailroadFreightCar, PassengerRailroadCar;
    public static RailroadCars pickRandomCar(){
        int carChoice = (int)Math.round(Math.random()*RailroadCars.values().length);
        switch(carChoice){
            case 0  -> { return BasicRailroadFreightCar; }
            case 1  -> { return HeavyRailroadFreightCar; }
            default -> { return PassengerRailroadCar;    }
        }
    }
}
