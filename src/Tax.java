public class Tax extends Location{

    private int taxAmount;

    public Tax(int cost, String name) {
        super(cost, name);
        this.taxAmount = 75;
    }

    @Override
    public void locationElementFunctionality(Player p) {
        // player method to lose money
    }

    @Override
    public String toString() {
        return null;
    }
}
