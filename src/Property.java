import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Property extends Location {
    private final List<Integer> rentCosts;
    private int numOfHouses;
    private final String color;
    private Player owner;
    private final int numberOfColor;

    public Property(String name, int cost, int initialRent,
                    int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, String color, int numOfColor){
        super(cost, name);
        this.rentCosts = new ArrayList<>(){{
           add(initialRent);
           add(house1Rent);
           add(house2Rent);
           add(house3Rent);
           add(house4Rent);
           add(hotelRent);
        }};
        this.numOfHouses = 0;
        this.color = color;
        this.owner = null;
        this.numberOfColor = numOfColor;
    }

    public void addHouse(){
        this.numOfHouses++;
    }

    @Override
    public void locationElementFunctionality(Player p) {
        Scanner sc = new Scanner(System.in);
        if (this.owner == null){
            System.out.println("You landed on" + this.toString());
            System.out.println("Would you want to \n(1) purchase \n (2) pass?");

            // sc.nextLine();
            // give p an option to either buy or pass
        }
        else {
            if (this.owner.equals(p)) {
                System.out.println();
                // buy a some houses if not maxed already...etc
                return;
            }
            System.out.println("You landed on " + toString() + " Owned by " + this.owner.getPlayerName());
            System.out.println("You lose money now :)");
            int doubleAmount = 1;

            int landedPlayerMoney = p.getMoneyAmount();
            if (landedPlayerMoney <= this.rentCosts.get(numOfHouses)){
                this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                return;
            }
            p.setMoneyAmount(p.getMoneyAmount() - this.rentCosts.get(numOfHouses));
            // p has to pay according to the number of houses the property owns
        }
    }

    public String toString(){
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        return "Property name: " + this.getName() + " {Rent: " + this.rentCosts.get(numOfHouses) + "}";

    }

}
