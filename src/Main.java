import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //DEFAULT PARAMETERS
        int numStations = 100;
        int numTrains = 25;
        int numRCars = 3;
        ArrayList<Locomotive> userLocomotives = new ArrayList<>();
        ArrayList<RailroadCar> userRailroadCars = new ArrayList<>();
        ArrayList<Station> userStations = new ArrayList<>();
        Graph<Station> userGraph = new Graph<>();
        RandomGraph<Station> randomGraph = new RandomGraph<Station>(numStations, "Station");
        userGraph.connectAll(randomGraph);
        userStations.addAll(randomGraph.getStations());
        ArrayList<TrainSet> userTrains = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu");
            System.out.println("1. Create a new locomotive");
            System.out.println("2. Create a new railroad car");
            System.out.println("3. Create a new railway station");
            System.out.println("4. Create a connection between stations");
            System.out.println("5. Attach a railroad car to a locomotive");
            System.out.println("6. Choose number of random TrainSets");
            System.out.println("7. Choose number of RailroadCars for Random Trains");
            System.out.println("8. Start the program");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    // create a new locomotive
                    System.out.println("Enter the name for the Locomotive: ");
                    String locName = scanner.next();
                    userLocomotives.add(new Locomotive(locName));
                    break;
                case 2:
                    // create a new railroad car
                    System.out.println("Select the type of railroad car to create:");
                    System.out.println("1. BasicRailroadFreightCar");
                    System.out.println("2. HeavyRailroadFreightCar");
                    System.out.println("3. PassengerRailroadCar");
                    System.out.print("Enter your choice: ");
                    int carTypeChoice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character
                    RailroadCar newCar = null;
                    switch (carTypeChoice) {
                        case 1:
                            // create a new BasicRailroadFreightCar
                            System.out.print("Enter the type of goods (COAL, WOOD, or SAND): ");
                            LightGoods goodsType = LightGoods.valueOf(scanner.nextLine().toUpperCase());
                            newCar = new BasicRailroadFreightCar(goodsType);
                            break;
                        case 2:
                            // create a new HeavyRailroadFreightCar
                            System.out.print("Enter the type of goods (IRON or GOLD): ");
                            HeavyGoods heavyGoodsType = HeavyGoods.valueOf(scanner.nextLine().toUpperCase());
                            newCar = new HeavyRailroadFreightCar(heavyGoodsType);
                            break;
                        case 3:
                            // create a new PassengerRailroadCar
                            System.out.print("Enter the type of passengers (PEOPLE, ELVES, or ALIENS): ");
                            Passengers passengerType = Passengers.valueOf(scanner.nextLine().toUpperCase());
                            newCar = new PassengerRailroadCar(passengerType);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (newCar != null) {
                        // add the new car to the list of cars
                        // TODO: add code to add new car to list
                        userRailroadCars.add(newCar);
                        System.out.println("New car created: " + newCar.toString());
                    }
                    break;
                case 3:
                    // create a new railway station
                    System.out.println("Enter name for the Station: ");
                    String sName = scanner.next();
                    Station station = new Station(sName);
                    userStations.add(station);
                    break;
                case 4:
                    // create a connection between stations
                    if (userStations.isEmpty()) {
                        System.out.println("There are no stations to connect. Please create a new station first.");
                        break;
                    }
                    // select the first station
                    System.out.println("Select the first station: ");
                    for (int i = 0; i < userStations.size(); i++) {
                        System.out.println(i + ". " + userStations.get(i));
                    }
                    int stationIndex1 = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character

                    // select the second station
                    System.out.println("Select the second station: ");
                    for (int i = 0; i < userStations.size(); i++) {
                        System.out.println(i + ". " + userStations.get(i));
                    }
                    int stationIndex2 = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character

                    if (stationIndex1 < 0 || stationIndex1 >= userStations.size() || stationIndex2 < 0 || stationIndex2 >= userStations.size()) {
                        System.out.println("Invalid station index. Please try again.");
                        break;
                    }

                    Station station1 = userStations.get(stationIndex1);
                    Station station2 = userStations.get(stationIndex2);

                    // create the connection
                    userGraph.connect(station1, station2);

                    System.out.println("Connection created between " + station1 + " and " + station2);
                    break;
                case 5:
                    System.out.println("Enter name for the TrainSet: ");
                    String tName = scanner.next();
                    // create a TrainSet with the selected locomotive and railroad car
                    TrainSet trainSet = new TrainSet(tName);

                    System.out.println("Select a locomotive by index:");
                    for (int i = 0; i < userLocomotives.size(); i++) {
                        System.out.println(i + ". " + userLocomotives.get(i));
                    }
                    int locIndex = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character

                    Locomotive selectedLocomotive = userLocomotives.get(locIndex);
                    trainSet.setLocomotive(selectedLocomotive);
                    userTrains.add(trainSet);

                    double maxWeight = selectedLocomotive.getMaxWeight();
                    int maxECars = selectedLocomotive.getMaxECars();
                    int maxRailroadCars = selectedLocomotive.getMaxRailroadCars();
                    double weight = 0;
                    int eCars = 0;
                    int rCars = 0;
                    int carIndex;
                    do {
                        System.out.println("Select a railroad car by index (-1 for stop):");
                        for (int i = 0; i < userRailroadCars.size(); i++) {
                            System.out.println(i + ". " + userRailroadCars.get(i));
                        }
                        carIndex = scanner.nextInt();
                        scanner.nextLine(); // consume the newline character
                        if (carIndex != -1) {
                            RailroadCar selectedRailroadCar = userRailroadCars.get(carIndex);
                            if(
                                       (weight + selectedRailroadCar.grossWeight <= maxWeight)
                                    && ((rCars + 1) <= maxRailroadCars)
                                    && ((eCars + 1) <= maxECars)
                            ) {
                                trainSet.attachRailroadCar(selectedRailroadCar);
                                weight += selectedRailroadCar.grossWeight;
                                if (selectedRailroadCar instanceof ElectricalGrid) eCars++;
                                rCars++;
                                System.out.println("Successfully attached " + selectedRailroadCar + " to " + selectedLocomotive);
                            }else{
                                System.out.println("WARNING: You exceeded the limit. The TrainSet was not changed.");
                            }
                        } else {
                            System.out.print("Successfully Created TrainSet with such RailroadCars: ");
                            System.out.println(trainSet.getRCars());
                        }
                    } while (carIndex != -1);
                    break;
                case 6:
                    // Choose number of random TrainSets
                    System.out.println("Choose number of random TrainSets: ");
                    numTrains = scanner.nextInt();
                    break;
                case 7:
                    // Choose number of RailroadCars for random Trains
                    System.out.println("Choose number of RailroadCars for random Trains: ");
                    numRCars = scanner.nextInt();
                    break;
                case 8:
                    System.out.println("Starting!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
            clearScreen();
        } while (choice != 8);

        ArrayList<TrainSet> trains = TrainSet.generateRandomSet(numTrains, numRCars, "Bullet Train", userGraph.getStations(), userGraph);
        for(TrainSet t : userTrains){
            t.setStationsLayout(userGraph);
        }
        trains.addAll(userTrains);

        ThreadedSetOff[] trainsThreads = TrainSet.setOff(trains);
        ThreadedOutput to = new ThreadedOutput(trains);
        to.start();
    }
    public static void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
    }
}
