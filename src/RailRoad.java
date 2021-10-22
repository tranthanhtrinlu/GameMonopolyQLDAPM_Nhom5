import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RailRoad extends Location{
    private List<Integer> payments;
    private Player owner;

    public RailRoad(String name, int cost){
        super(cost, name);
        this.payments = new ArrayList<>(){{
           add(25);
           add(50);
           add(100);
           add(200);
        }};
        this.owner = null;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        Scanner sc = new Scanner(System.in);
        int userInput;
        if (this.owner == null){
            System.out.println("You landed on " + this.toString());
            while(true) {
                try {
                    System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                    userInput = sc.nextInt();
                    if (userInput < 0 || userInput > 2) {
                        System.out.println("invalid input");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter an integer.");
                }
            }
            if (userInput == 1){
                if (p.getMoneyAmount() < this.getCost()){
                    System.out.println("Sorry, you don't have enough money. Moving to the next player");
                    return;
                }
                this.owner = p;
                this.owner.addProperty(this);
                this.owner.addNumOfRailroads();
            }
        }
        else {
            if (!this.owner.equals(p)) {
                System.out.println("You landed on " + this.toString() + " Owned by " + this.owner.getPlayerName());
                System.out.println("You lose money now :)");
                int landedPlayerMoney = p.getMoneyAmount();
                int payment = this.payments.get(this.owner.getNumOfRailroads());
                if (landedPlayerMoney <= payment){
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                    return;
                }
                p.setMoneyAmount(p.getMoneyAmount() - payment);
                this.owner.setMoneyAmount(p.getMoneyAmount() + payment);
                return;
            }
            System.out.println("You landed on " + this.getName() + " Which you own");
        }
    }

    @Override
    public String toString() {
        if (this.owner == null)
            return this.getName() + " {Cost is: " + this.getCost() + "}";
        return this.getName() + " {Current Price: " + this.payments.get(this.owner.getNumOfRailroads()) + "}";
    }
}
