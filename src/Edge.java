import java.util.*;

public class Edge<T extends Node> {
    private LinkedList<T> path;
    private T start;
    private T destination;
    private double distance;
    private final double distanceBetweenTwoStations = 1500;

    public Edge(T start, T destination, HashMap<T, HashSet<T>> adjacencyList){
        LinkedList<T> currentPosition = new LinkedList<>();
        HashSet<T> visited = new HashSet<>();
        this.start = start;
        this.destination = destination;
        this.path = calculatePath(start, destination, currentPosition, adjacencyList, visited); // Rename  CurrentPosition
        this.distance = this.path.size() * distanceBetweenTwoStations;

    }
    //Depth-First Search Algorithm implementation
    public LinkedList<T> calculatePath(T start, T destination, LinkedList<T> currentPosition, HashMap<T, HashSet<T>> adjacencyList, HashSet<T> visited) throws EdgeNotFoundException{
        for (T n : adjacencyList.getOrDefault(start, new HashSet<T>())){
            if (n == destination){
                currentPosition.add(start);
                currentPosition.add(destination);
                return currentPosition;
            }
            if(!visited.contains(n)) {
                LinkedList<T> tempCurrentPosition = new LinkedList<>(currentPosition);
                tempCurrentPosition.add(start);
                visited.add(n);
                return calculatePath(n, destination, tempCurrentPosition, adjacencyList, visited);
            }
        }
        throw new EdgeNotFoundException("There was no connection found between " + start + " and " + destination);
    }
    public LinkedList<T> getPath(){return path;}
    public T getStart(){return start;}
    public T getDestination(){return destination;}
    public double getDistance(){return distance;}

    public double getDistanceBetweenTwoStations(){return distanceBetweenTwoStations;}

    @Override
    public String toString(){
        return getPath().toString();
    }
}
