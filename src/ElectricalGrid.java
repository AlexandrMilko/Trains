import java.util.ArrayList;
public class ElectricalGrid<T> {
    private String name;
    private ArrayList<T> connected = new ArrayList<>();
    public ElectricalGrid(String name){
        this.name = name;
    }
    public void connect(T obj){
        connected.add(obj);
    }
    public boolean isConnected(T obj){
        for (T el : connected) if(obj == el) return true;
        return false;
    }
}