public abstract class Location {
    private int cost;
    private String name;

    public Location(int cost, String name){
        this.cost = cost;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }


    public abstract void locationElementFunctionality(Player p);

    public abstract String toString();

}
