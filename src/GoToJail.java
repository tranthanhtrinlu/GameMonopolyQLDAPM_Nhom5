public class GoToJail extends Location{

    public GoToJail(int cost, String name) {
        super(cost, name);
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        System.out.println("You landed on GO TO JAIL, so you going to jail :)");
        p.setPosition(BoardModel.JAIL_POSITION);
        p.setInJail(true);
    }

    @Override
    public String toString() {
        return null;
    }
}
