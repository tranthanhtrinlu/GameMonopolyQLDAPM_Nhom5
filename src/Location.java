public abstract class Location {
    private int cost;
    private String name;
    private Player owner;

    public Location(String name, int cost){
        this.name = name;
        this.cost = cost;
        this.owner = null;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return this.owner;
    }
}
