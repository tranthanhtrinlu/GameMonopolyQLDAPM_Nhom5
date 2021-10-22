public class LandOnJail extends Location {

    public LandOnJail(int cost, String name) {
        super(cost, name);
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        System.out.println("You landed " + this.toString());
        System.out.println("You are just Visiting, moving to the next player");
    }

    @Override
    public String toString() {
        return "On Jail";
    }
}
