public abstract class Location {
    private final int cost;
    private final String name;

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


    public abstract void locationElementFunctionality(Player p, int totalDiceRoll);

    public abstract String toString();

}
