import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.lang.StringBuilder;
public class Graph<T extends Node> {
    private HashMap<T, HashSet<T>> adjacencyList = new HashMap<>();
    private HashSet<T> stations = new HashSet<>();

    public Graph(ArrayList<T> nodes){
        for(T n : nodes){
            adjacencyList.put(n, new HashSet<T>());
        }
        stations.addAll(nodes);
    }
    public void connect(T node1, T node2){
        HashSet<T> nodesBefore = adjacencyList.getOrDefault(node1, new HashSet<T>());
        nodesBefore.add(node2);
        adjacencyList.put(node1, nodesBefore);
        if(!stations.contains(node2)) stations.add(node2);
    }

    //Breadth-First Search Algorithm implementation
    public boolean areConnected(T node1, T node2){
        HashSet<T> visited = new HashSet<>();
        Queue<T> q = new LinkedList<T>();
        q.add(node1);
        while(q.size() > 0){
            T n = q.poll();
            HashSet<T> destinations = adjacencyList.getOrDefault(n, new HashSet<T>());
            for(T d : destinations){
                if(d == node2) return true;
                if(!visited.contains(d)){
                    q.add(d);
                    visited.add(d);
                }
            }
        }
        return false;
    }
    public Edge<T> getEdge(T node1, T node2) throws EdgeNotFoundException{
        if(areConnected(node1, node2)){
            return new Edge<T>(node1, node2, adjacencyList);
        }
        throw new EdgeNotFoundException("There was no connection found between " + node1 + " and " + node2);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<T, HashSet<T>> e : adjacencyList.entrySet()){
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
    public HashMap<T, HashSet<T>> getAdjacencyList(){return adjacencyList;}
    public HashSet<T> getStations(){return stations;}
}
