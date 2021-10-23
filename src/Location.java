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


    public abstract boolean locationElementFunctionality(Player p, int totalDiceRoll);

    public abstract String toString(Player p);

    public abstract boolean buy(Player p);

    public abstract void resetOwner();

    public abstract void getResult(Player p, BoardEvent event);

    public abstract void addListener(BoardView view);
}
