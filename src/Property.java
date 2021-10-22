import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Property extends Location {
    private final List<Integer> rentCosts;
    private int numOfHouses;
    private final int maxNumberOfHouses;
    private final BoardModel.Color color;
    private Player owner;
    private final int numberOfColor;
    private final int costPerHouse;


    public Property(String name, int cost, int costPerHouse, int initialRent, int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, BoardModel.Color color, int numOfColor){
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
        this.maxNumberOfHouses = 5;
        this.color = color;
        this.owner = null;
        this.numberOfColor = numOfColor;
        this.costPerHouse = costPerHouse;
    }

    public boolean addHouse(int add, Player p){
        if (this.numOfHouses+add <= this.maxNumberOfHouses && p.getMoneyAmount() <= add*this.costPerHouse) {
            this.numOfHouses += add;
            return true;
        }
        return false;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        Scanner sc = new Scanner(System.in);
        int userInput;
        if (this.owner == null){ // if the owner is non existent
            System.out.println("You landed on " + this.toString());
            while(true){
                try{
                    System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                    userInput = sc.nextInt();
                    if(userInput < 0 || userInput > 2){
                        System.out.println("invalid input");
                        continue;
                    }
                    break;
                }catch(InputMismatchException e){
                    System.out.println("You did not enter an integer.");
                }
            }
            if (userInput == 1) {
                if (p.getMoneyAmount() < this.getCost()){
                    System.out.println("Sorry, you don't have enough money. Moving to the next player");
                    return;
                }
                this.owner = p;
                this.owner.addProperty(this);
                this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
                //FIX THIS to ask if they want to buy some properties
                this.owner.addColorToProperty(this.color, 1);
                boolean buy = false;
                while (true) {
                    try{
                        while(true){
                            System.out.println("Would you want to buy any houses?\n(1) YES or (2) No");
                            userInput = sc.nextInt();
                            if (userInput < 0 || userInput > 2){
                                System.out.println("Invalid Input");
                                continue;
                            }
                            if (userInput == 1){
                                buy = true;
                            }
                            break;
                        }
                        if (!buy){
                            break;
                        }
                        System.out.println("Each house cost " + this.costPerHouse);
                        System.out.println("Out of " + this.maxNumberOfHouses + ", how many would you like to buy?");
                        userInput = sc.nextInt();
                        if(!this.addHouse(userInput, p)){
                            System.out.println("invalid amount or not enough money");
                            continue;
                        }
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("You did not enter an integer.");
                    }
                }
            }
        }
        else {
            if (this.owner.equals(p)) {
                System.out.println("You landed on " + this.toString() + " \nA property you own");
                if (this.numOfHouses != this.maxNumberOfHouses){
                    while(true){
                        try{
                            System.out.println("Would you want to \n(1) purchase some buildings \n(2) pass?");
                            userInput = sc.nextInt();
                            if(userInput < 0 || userInput > 2){
                                System.out.println("invalid input");
                                continue;
                            }
                            break;
                        }catch(InputMismatchException e){
                            System.out.println("You did not enter an integer.");
                        }
                    }
                    if (userInput == 1){
                        while(true){
                            try{
                                System.out.println("Each house cost " + this.costPerHouse);
                                System.out.println("You currently have " + this.numberOfColor + " of " + this.maxNumberOfHouses);
                                System.out.println("How many would you like to currently buy?");
                                userInput = sc.nextInt();
                                if(!this.addHouse(userInput, p)){
                                    System.out.println("invalid amount or not enough money");
                                    continue;
                                }
                                break;
                            }catch(InputMismatchException e){
                                System.out.println("You did not enter an integer.");
                            }
                        }
                    }
                    return;
                }
                System.out.println("You maxed out on houses already");
                return;
            }
            System.out.println("You landed on " + toString() + " Owned by " + this.owner.getPlayerName());
            System.out.println("You lose money now :)");
            int doubleAmount = 1;
            if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
                doubleAmount = 2;
            int landedPlayerMoney = p.getMoneyAmount();
            if (landedPlayerMoney <= this.rentCosts.get(numOfHouses)){
                this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                return;
            }
            p.setMoneyAmount(p.getMoneyAmount() - this.rentCosts.get(numOfHouses));
            this.owner.setMoneyAmount(p.getMoneyAmount() + (this.rentCosts.get(numOfHouses) * doubleAmount));
        }
    }

    public String toString(){
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        return "Property name: " + this.getName() + " {Rent: " + this.rentCosts.get(numOfHouses) + "}";

    }
}
