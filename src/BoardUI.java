import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
//
public class BoardUI implements BoardView{

    protected BoardModel model;
    private final Scanner sc;
    private int userInput;
    private List<Player> gamePlayers;

    public BoardUI(){
        this.sc = new Scanner(System.in);
        this.gamePlayers = new ArrayList<>();
        this.model = new BoardModel();
        this.model.addView(this);
    }

    private int getNumPlayers(){
        while(true){
            try{
                System.out.println("Enter number of players?(Min: " + BoardModel.MIN_NUM_PLAYERS + " Max: " + BoardModel.MAX_NUM_PLAYERS);
                userInput = sc.nextInt();
                if(userInput >= BoardModel.MIN_NUM_PLAYERS && userInput <= BoardModel.MAX_NUM_PLAYERS){
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("You did not enter an integer.");
            }
        }
        return userInput;
    }

    private void getPlayerNames(int numPlayers){
        Scanner sc = new Scanner(System.in);
        for(int i = 0;i <numPlayers;i++){
            System.out.print("Player #"+ (i + 1) +" Enter your name: ");
            String name = sc.next();
            this.gamePlayers.add(new Player(name));
        }
    }

    //****BEGINNING OF PROPERTY FUNCTIONS**//
    @Override
    public void propertyNoOwner(PropertyEvent e) {
        System.out.println("You landed on " + e.getProperty().toString());
        while(true){
            try{
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                this.userInput = this.sc.nextInt();
                if(this.userInput < 0 || this.userInput > 2){
                    System.out.println("invalid input");
                    continue;
                }
                break;
            }catch(InputMismatchException exception){
                System.out.println("You did not enter an integer.");
            }
        }
        if (userInput == 1) {
            if (e.getProperty().buy(e.getPlayer())){
                System.out.println("Not enough Money");
                return;
            }
            boolean buy = false;
            while (true) {
                try{
                    while(true){
                        System.out.println("Would you want to buy any houses?\n(1) YES or (2) No");
                        this.userInput = this.sc.nextInt();
                        if (this.userInput < 0 || this.userInput > 2){
                            System.out.println("Invalid Input");
                            continue;
                        }
                        if (this.userInput == 1){
                            buy = true;
                        }
                        break;
                    }
                    if (!buy){
                        break;
                    }
                    System.out.println("Each house cost " + e.getProperty().getCostPerHouse());
                    System.out.println("Out of " + e.getProperty().getMaxNumberOfHouses() + ", how many would you like to buy?");
                    this.userInput = this.sc.nextInt();
                    if(!e.getProperty().addHouse(this.userInput, e.getPlayer())){
                        System.out.println("invalid amount or not enough money");
                        continue;
                    }
                    break;
                }catch(InputMismatchException exception){
                    System.out.println("You did not enter an integer.");
                }
            }
        }
    }

    @Override
    public void propertyOwned(PropertyEvent e) {
        System.out.println("You landed on " + e.getProperty().toString() + " \nA property you own");
        if (e.getProperty().getNumOfHouses() != e.getProperty().getMaxNumberOfHouses()){
            while(true){
                try{
                    System.out.println("Would you want to \n(1) purchase some buildings \n(2) pass?");
                    this.userInput = this.sc.nextInt();
                    if(this.userInput < 0 || this.userInput > 2){
                        System.out.println("invalid input");
                        continue;
                    }
                    break;
                }catch(InputMismatchException exception){
                    System.out.println("You did not enter an integer.");
                }
            }
            if (this.userInput == 1){
                while(true){
                    try{
                        System.out.println("Each house cost " + e.getProperty().getCostPerHouse());
                        System.out.println("You currently have " + e.getProperty().getNumOfHouses() + " of " + e.getProperty().getMaxNumberOfHouses());
                        System.out.println("How many would you like to currently buy?");
                        this.userInput = this.sc.nextInt();
                        if(!e.getProperty().addHouse(this.userInput, e.getPlayer())){
                            System.out.println("invalid amount or not enough money");
                            continue;
                        }
                        break;
                    }catch(InputMismatchException exception){
                        System.out.println("You did not enter an integer.");
                    }
                }
            }
            return;
        }
        System.out.println("You maxed out on houses already");
    }

    @Override
    public void propertyRent(PropertyEvent e) {
        Player owner = e.getProperty().getOwner();
        Player landedPlayer = e.getPlayer();
        System.out.println("You landed on " + e.getProperty().toString() + " Owned by " + owner.getPlayerName());
        System.out.println("You lose money now :)");
        int doubleAmount = 1;
        if (owner.numberOfColoredPropertiesOwned(e.getProperty().getColor(), e.getProperty().getNumberOfColor()))
            doubleAmount = 2;
        int landedPlayerMoney = landedPlayer.getMoneyAmount();
        int rentCost = e.getProperty().getRentCost(e.getProperty().getNumOfHouses());
        if (landedPlayerMoney <= rentCost){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            return;
        }
        landedPlayer.setMoneyAmount(landedPlayer.getMoneyAmount() - rentCost);
        owner.setMoneyAmount(landedPlayer.getMoneyAmount() + (rentCost * doubleAmount));
    }
    // ** ENDING OF PROPERTY IMPLEMENTATION

    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//
    @Override
    public void railRoadNoOwner(RailRoadEvent e) {
        System.out.println("You landed on " + e.getRailRoad().toString());
        while(true) {
            try {
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput > 2) {
                    System.out.println("invalid input");
                    continue;
                }
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You did not enter an integer.");
            }
        }
        if (userInput == 1){
            if (e.getRailRoad().buy(e.getPlayer())){
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
            }
        }
    }

    @Override
    public void railRoadOwned(RailRoadEvent e) {
        System.out.println("You own landed on " + e.getRailRoad().getName() + ", which you own");
    }

    @Override
    public void railRoadRent(RailRoadEvent e) {
        Player owner = e.getRailRoad().getOwner();
        System.out.println("You landed on " + e.getRailRoad().toString() + " Owned by " + owner.getPlayerName());
        System.out.println("You lose money now :)");
        int landedPlayerMoney = e.getPlayer().getMoneyAmount();
        int payment = e.getRailRoad().getPayment(owner.getNumOfRailroads());
        if (landedPlayerMoney <= payment){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            return;
        }
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - payment);
        owner.setMoneyAmount(e.getPlayer().getMoneyAmount() + payment);
    }

    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    @Override
    public void UtilityNoOwner(UtilityEvent e) {
        System.out.println("You landed on " + e.getUtility().toString());
        while(true) {
            try {
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput > 2) {
                    System.out.println("invalid input");
                    continue;
                }
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You did not enter an integer.");
            }
        }
        if (this.userInput == 1){
            if (e.getUtility().buy(e.getPlayer())){
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
            }
        }
    }

    @Override
    public void UtilityOwned(UtilityEvent e) {
        System.out.println("You landed on " + e.getUtility().getName() + " Which you own");
    }

    @Override
    public void UtilityPay(UtilityEvent e) {
        Player owner = e.getUtility().getOwner();
        System.out.println("You landed on " + this.toString() + " Owned by " + owner.getPlayerName());
        int landedPlayerMoney = e.getPlayer().getMoneyAmount();
        int amount = 4;
        if (owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        int payment = e.getTotalDiceRoll()*amount;
        if (landedPlayerMoney <= payment){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            return;
        }
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - payment);
        owner.setMoneyAmount(e.getPlayer().getMoneyAmount() + payment);
    }
    // ** END OF UTILITY IMPLEMENTATION **

    @Override
    public void FreeParking(Tax_FreeParkingEvent e) {
        if (e.getLocation().getCenterMoney() == 0)
            e.getLocation().setCenterMoney(100);
        System.out.println("You landed on free parking, you will receive $" + e.getLocation().getCenterMoney());
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() + e.getLocation().getCenterMoney());
        e.getLocation().setCenterMoney(0);
    }

    @Override
    public void payTax(Tax_FreeParkingEvent e) {
        System.out.println("You landed on " + e.getLocation().getName() + ", you will lose $" + e.getLocation().getCost());
        if (e.getPlayer().getMoneyAmount() <= e.getLocation().getCost()){
            e.getLocation().addToCenterMoney(e.getPlayer().getMoneyAmount());
            return;
        }
        e.getLocation().addToCenterMoney(e.getLocation().getCost());
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - e.getLocation().getCost());
    }
    // END OF IMPLEMENTING TAX AND PARKING //

    // BEGINNING OF IMPLEMENTING LAND ON JAIL //
    @Override
    public void visiting(LandOnJailEvent e) {
        System.out.println("You landed " + e.getLandOnJail().toString());
        System.out.println("You are just Visiting, moving to the next player");
    }

    // END OF IMPLEMENTING TAX AND PARKING //

    // BEGINNING OF IMPLEMENTING LAND ON JAIL //
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        System.out.println("You landed on " + e.getGoToJail().toString() + " so you going to jail :)");
        e.getPlayer().setPosition(BoardModel.JAIL_POSITION);
        e.getPlayer().setInJail(true);
    }

    // BEGINNING OF ENDING LAND ON JAIL //

    @Override
    public void FreePass(FreePassEvent e) {
        System.out.println("You landed on a + " + e.getPass().toString() + " Enjoy it :)");
    }


}
