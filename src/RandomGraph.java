import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
public class RandomGraph<T> extends Graph{

    private Random randomGen = new Random();
    private final int vertices;
    private final int edges;

    private final int MAX_LIMIT = 30;
    private final int MIN_LIMIT = 10;
    public RandomGraph(int vertices, int edges, String nodeName) throws TooManyEdgesException{
        //We will use connect method later to add all the nodes.
        //But since we do not have them yet,
        //we just pass an empty array
        super(new ArrayList<T>());
        this.vertices = vertices;
        this.edges = edges;
        if(this.edges > calculateMaxEdges(this.vertices)) throw new TooManyEdgesException(
                "You exeeded the limit of edges for specified vertices number"
        );

        // CREATE an array of nodes
        ArrayList<Station> nodes = new ArrayList<>(); //TODO use generics instead of Station?
        for(int i=0; i<vertices; ++i){
            nodes.add(new Station(nodeName));
        }

        //Randomly choose from nodes and connect together
        for(int i=0; i<edges; ++i){
            int v = randomGen.nextInt(this.vertices);
            int w = randomGen.nextInt(this.vertices);
            connect(nodes.get(v), nodes.get(w));
        }
    }
    public RandomGraph(int vertices, String nodeName){
        //We will use connect method later to add all the nodes.
        //But since we do not have them yet,
        //we just pass an empty array
        super(new ArrayList<T>());
        this.vertices = vertices;
        int maxEdges = calculateMaxEdges(this.vertices);
        this.edges = randomGen.nextInt(maxEdges);

        // CREATE an array of nodes
        ArrayList<Station> nodes = new ArrayList<>(); //TODO use generics instead of Station?
        for(int i=0; i<vertices; ++i){
            nodes.add(new Station(nodeName));
        }

        //Randomly connect them together
        for(int i=0; i<edges; ++i){
            int v = randomGen.nextInt(this.vertices);
            int w = randomGen.nextInt(this.vertices);
            connect(nodes.get(v), nodes.get(w));
        }
    }
    public RandomGraph(String nodeName){
        //We will use connect method later to add all the nodes.
        //But since we do not have them yet,
        //we just pass an empty array
        super(new ArrayList<T>());
        this.vertices = randomGen.nextInt(MAX_LIMIT-MIN_LIMIT) + MIN_LIMIT;
        int maxEdges = calculateMaxEdges(this.vertices);
        this.edges = randomGen.nextInt(maxEdges);

        // CREATE an array of nodes
        ArrayList<Station> nodes = new ArrayList<>(); //TODO use generics instead of Station?
        for(int i=0; i<vertices; ++i){
            nodes.add(new Station(nodeName));
        }

        //Randomly connect them together
        for(int i=0; i<edges; ++i){
            int v = randomGen.nextInt(this.vertices);
            int w = randomGen.nextInt(this.vertices);
            connect(nodes.get(v), nodes.get(w));
        }
    }

    // vertices-1 (we do not include reflexive Edges) and we divide this number by 2 because,
    // it is directed graph
    private int calculateMaxEdges(int vertices){
        return (vertices*(vertices-1)/2);
    }
}
