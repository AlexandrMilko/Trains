public class Station implements Node{
    private String name;
    private static int lastId = 0;
    private int id;
    public Station(String name){
        this.name = name;
        this.id = lastId++;
    }
    public int getId(){return id;}

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Station objCast = (Station) obj;
        return this.getId() == objCast.getId();
    }
    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public String toString(){
        return name + "(" + id + ")";
    }
}
