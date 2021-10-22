public class FreePass extends Location{

    public FreePass(int cost, String name) {
        super(cost, name);
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        System.out.println("You landed on a free pass, Enjoy it :)");
    }

    @Override
    public String toString() {
        return null;
    }
}
