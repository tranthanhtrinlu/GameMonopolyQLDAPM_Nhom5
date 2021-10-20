public class Utility extends Location{

    private Player owner;
    private int amount;

    public Utility(int cost, String name) {
        super(cost, name);
        this.amount = 10;
        this.owner = null;
    }

    @Override
    public void locationElementFunctionality(Player p) {
        if (this.owner == null){
            // give p an option to either buy or pass
        }
        else {
            if (!this.owner.equals(p)) {
                // check if player landed is not the player owner
            }
        }
    }

    @Override
    public String toString() {
        return "Utility: " + this.getName() + " {Price: dice roll * " + this.amount + "}";
    }
}
