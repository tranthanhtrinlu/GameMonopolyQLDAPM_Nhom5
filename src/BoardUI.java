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
    private int currentTurn;
    private boolean pass, purchasesProperty;

    public BoardUI(){
        this.sc = new Scanner(System.in);
        this.gamePlayers = new ArrayList<>();
        this.model = new BoardModel();
        this.model.addView(this);
        this.currentTurn = 0;
        this.pass = false;
        this.purchasesProperty = false;
    }

    private int getNumPlayers(){
        while(true){
            try{
                System.out.println("Enter number of players? (Min: " + BoardModel.MIN_NUM_PLAYERS + " Max: " + BoardModel.MAX_NUM_PLAYERS + ")");
                System.out.print("> ");
                this.userInput = this.sc.nextInt();
                if(this.userInput >= BoardModel.MIN_NUM_PLAYERS && this.userInput <= BoardModel.MAX_NUM_PLAYERS){
                    break;
                }
                System.out.println("Invalid amount");
            }catch(InputMismatchException e){
                System.out.println("You did not enter an integer.");
            }
        }
        return this.userInput;
    }

    private void getPlayerNames(int numPlayers){
        for(int i = 0;i <numPlayers;i++){
            System.out.print("Player #"+ (i + 1) +" Enter your name: ");
            String name = this.sc.next();
            this.gamePlayers.add(new Player(name));
        }
    }

    private void bankruptedPlayer(Player p){
        System.out.println("Player: " + p.getPlayerName() + " has no more money. Removing player from game.");
        System.out.println("Player properties are now back in estate!");
        p.bankrupted();
        this.gamePlayers.remove(p);
    }

    public boolean checkWinner(){
        if (this.gamePlayers.size() == 1){
            System.out.println("Winner is " + this.gamePlayers.get(0).getPlayerName() + " Ending game now");
            return true;
        }
        return false;
    }


    //****BEGINNING OF PROPERTY FUNCTIONS**//
    @Override
    public void propertyNoOwner(PropertyEvent e) {
        while(true){
            try{
                System.out.println("You landed on " + e.getProperty().getName() + " Cost is " + e.getProperty().getCost());
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
                this.pass = true;
                System.out.println("Not enough Money, moving to the next player");
                return;
            }
            this.purchasesProperty = true;
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
        this.pass = true;
    }

    @Override
    public void propertyOwned(PropertyEvent e) {
        if (e.getProperty().getNumOfHouses() != e.getProperty().getMaxNumberOfHouses()){
            while(true){
                try{
                    System.out.println("You landed on " + e.getProperty().getName() + " Cost is " + e.getProperty().getCost());
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
            this.pass = true;
            return;
        }
        System.out.println("You maxed out on houses already");
        this.pass = true;
    }

    @Override
    public void propertyRent(PropertyEvent e) {
        Player owner = e.getProperty().getOwner();
        Player landedPlayer = e.getPlayer();
        System.out.println("You landed on " + e.getProperty().getName() + " Owned by " + owner.getPlayerName());
        System.out.println("You pay now");
        int doubleAmount = 1;
        if (owner.numberOfColoredPropertiesOwned(e.getProperty().getColor(), e.getProperty().getNumberOfColor()))
            doubleAmount = 2;
        int landedPlayerMoney = landedPlayer.getMoneyAmount();
        int rentCost = e.getProperty().getRentCost(e.getProperty().getNumOfHouses());
        if (landedPlayerMoney <= rentCost){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            this.bankruptedPlayer(landedPlayer);
            return;
        }
        landedPlayer.setMoneyAmount(landedPlayer.getMoneyAmount() - rentCost);
        owner.setMoneyAmount(landedPlayer.getMoneyAmount() + (rentCost * doubleAmount));
    }

    @Override
    public void displayLandedPropertyResult(PropertyEvent e,  BoardEvent event) {
        event.getModel().announcePropertyResult(event);
    }
    // ** ENDING OF PROPERTY IMPLEMENTATION

    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//
    @Override
    public void railRoadNoOwner(RailRoadEvent e) {
        while(true) {
            try {
                System.out.println("You landed on " + e.getRailRoad().getName() + " Cost is " + e.getRailRoad().getCost());
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
                this.pass = true;
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
                return;
            }
            return;
        }
        this.pass = true;
    }

    @Override
    public void railRoadOwned(RailRoadEvent e) {
        System.out.println("You landed on " + e.getRailRoad().getName() + " Which you own");
    }

    @Override
    public void railRoadRent(RailRoadEvent e) {
        Player owner = e.getRailRoad().getOwner();
        System.out.println("You landed on " + e.getRailRoad().getName() + " Owned by " + owner.getPlayerName());
        System.out.println("You pay now");
        int landedPlayerMoney = e.getPlayer().getMoneyAmount();
        int payment = e.getRailRoad().getPayment(owner.getNumOfRailroads());
        if (landedPlayerMoney <= payment){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            this.bankruptedPlayer(e.getPlayer());
            return;
        }
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - payment);
        owner.setMoneyAmount(e.getPlayer().getMoneyAmount() + payment);
    }

    @Override
    public void displayLandedRailroadResult(RailRoadEvent e, BoardEvent boardEvent) {
        boardEvent.getModel().announcePropertyResult(boardEvent);
    }

    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    @Override
    public void UtilityNoOwner(UtilityEvent e) {
        while(true) {
            try {
                System.out.println("You landed on " + e.getUtility().getName() + " Cost is " + e.getUtility().getCost());
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
                this.pass = true;
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
                return;
            }
            return;
        }
        this.pass = true;
    }

    @Override
    public void UtilityOwned(UtilityEvent e) {
        System.out.println("You landed on " + e.getUtility().getName() + " Which you own");
    }

    @Override
    public void UtilityPay(UtilityEvent e) {
        Player owner = e.getUtility().getOwner();
        System.out.println("You landed on " + e.getUtility().getName() + " Owned by " + owner.getPlayerName());
        System.out.println("You pay now");
        int landedPlayerMoney = e.getPlayer().getMoneyAmount();
        int amount = 4;
        if (owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        int payment = e.getTotalDiceRoll()*amount;
        if (landedPlayerMoney <= payment){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            this.bankruptedPlayer(e.getPlayer());
            return;
        }
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - payment);
        owner.setMoneyAmount(e.getPlayer().getMoneyAmount() + payment);
    }

    @Override
    public void displayLandedUtilityResult(UtilityEvent e, BoardEvent boardEvent) {
        boardEvent.getModel().announcePropertyResult(boardEvent);
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
            this.bankruptedPlayer(e.getPlayer());
            return;
        }
        e.getLocation().addToCenterMoney(e.getLocation().getCost());
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - e.getLocation().getCost());
    }
    // END OF IMPLEMENTING TAX AND PARKING //

    // BEGINNING OF IMPLEMENTING LAND ON JAIL //
    @Override
    public void visiting(LandOnJailEvent e) {
        System.out.println("You landed " + e.getLandOnJail().getName());
        System.out.println("You are just Visiting, moving to the next player");
    }

    // END OF IMPLEMENTING TAX AND PARKING //

    // BEGINNING OF IMPLEMENTING LAND ON JAIL //
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        System.out.println("You landed on " + e.getGoToJail().getName() + " so you going to jail :)");
        e.getPlayer().setPosition(BoardModel.JAIL_POSITION);
        e.getPlayer().setInJail(true);
    }
    // ENDING OF LAND ON JAIL //

    @Override
    public void FreePass(FreePassEvent e) {
        System.out.println("You landed on a + " + e.getPass().getName() + " Enjoy it :)");
    }

    // HANDLES
    @Override
    public boolean checkIfPlayerHasProperties(BoardEvent e){
        return this.gamePlayers.get(this.currentTurn).getSizeOfOwnedProperties() != 0;
    }

    @Override
    public void handlePrintStateOfEachPlayer(BoardEvent e) {
        for (Player p : this.gamePlayers){
            System.out.println(p.toString());
        }
    }

    @Override
    public void announceReachingGo() {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println(p.getPlayerName() + " received $" + BoardModel.GO_MONEY + " for reaching GO");
    }

    @Override
    public void handleGameStatus(BoardEvent e, boolean result) {
        if (result){
            e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).getResult(this.gamePlayers.get(this.currentTurn), e);
        }
    }

    @Override
    public void handleAnnounceLanded(BoardEvent e){
        Player p = this.gamePlayers.get(this.currentTurn);
        Location place = e.boardElement(p.getPosition());
        System.out.println(place.toString(p));
    }

    @Override
    public void handlePurchaseAnnouncment(BoardEvent e) {
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        Player player = this.gamePlayers.get(this.currentTurn);
        if (this.pass){
            System.out.println("Player: + " + player.getPlayerName() + " has done nothing. Moving to the next player");
            this.pass = false;
            return;
        }
        if (place instanceof Property){
            if (this.purchasesProperty){
                System.out.println("Player: " + player.getPlayerName() + " has purchase the property " + place.getName() + " and owns " + ((Property) place).getNumOfHouses());
                this.purchasesProperty = false;
            }
            System.out.println("Player: " + player.getPlayerName() + " has purchase + " + (((Property) place).getNumOfHouses() - ((Property) place).getOldNumOfHouses()) + " Houses for " + place.getName());

        }
        System.out.println("Player: " + player.getPlayerName() + " Has purchased " + place.getName());
    }

    @Override
    public void handleNextTurn(BoardEvent e) {
        this.currentTurn++;
        if (this.currentTurn == this.gamePlayers.size())
            this.currentTurn = 0;
    }

    @Override
    public void announcePlayerPass(BoardEvent e) {
        Player p = this.gamePlayers.get(currentTurn);
        System.out.println("Player " + p.getPlayerName() + " passed the turn. Moving to the next player.");
    }

    @Override
    public void handlePlayerQuit(BoardEvent e) {
        Player quittingPlayer = this.gamePlayers.get(currentTurn);
        System.out.println("Player: " + quittingPlayer.getPlayerName() + " has quit the game");
        quittingPlayer.bankrupted();
        this.gamePlayers.remove(quittingPlayer);
    }

    @Override
    public void handlePlayerChoiceToPurchaseHouses(BoardEvent e) {
        Player p = this.gamePlayers.get(this.currentTurn);
        while (true){
            try{
                System.out.println("Which property would you like to purchase houses for?");
                System.out.println(p.displayOwnedProperties());
                System.out.print("<number input>>> ");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput >= p.getSizeOfOwnedProperties()){
                    System.out.println("Invalid option");
                    continue;
                }
                Location place = p.getLocationByIndex(this.userInput);
            }catch(InputMismatchException exception){
                System.out.println("Invalid input");
            }
        }
    }

    @Override
    public void announceDecisionToPurchaseHouses(BoardEvent e) {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println("Player: " + p.getPlayerName() + " has decided to purchase houses for a property they own");
    }

    @Override
    public void handlePlayerMovement(BoardEvent e) {
        if (this.gamePlayers.get(this.currentTurn).movePlayer(e.getRoll())){
            e.getModel().announceReachingGo();
        }
        e.getModel().announceLanded(e);
        boolean result = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).locationElementFunctionality(this.gamePlayers.get(this.currentTurn), e.getRoll());
        e.getModel().announceLandedResult(e, result);
    }

    @Override
    public int handleCurrentPlayerChoice(BoardEvent e) {
        int numOfOptions;
        numOfOptions = 4;
        if (this.checkIfPlayerHasProperties(e)){
            numOfOptions = 5;
        }
        while (true){
            try{
                System.out.println("Current Player: " + this.gamePlayers.get(this.currentTurn).getPlayerName());
                System.out.println("You are currently at " + e.boardElementName(this.gamePlayers.get(this.currentTurn).getPosition()));
                if (numOfOptions == 5){
                    System.out.println("What would you like to do?");
                    System.out.println("(1) Print State of Each Player");
                    System.out.println("(2) Roll");
                    System.out.println("(3) Purchase house for an existing property?");
                    System.out.println("(4) Pass?");
                    System.out.println("(5) Quit");
                }
                else{
                    System.out.println("What would you like to do?");
                    System.out.println("(1) Print State of Each Player");
                    System.out.println("(2) Roll");
                    System.out.println("(3) Pass?");
                    System.out.println("(4) Quit");
                }
                this.userInput = sc.nextInt();
                if (this.userInput > 0 && this.userInput <= numOfOptions){
                    return this.userInput;
                }
                System.out.println("Invalid option");
            }catch(InputMismatchException exception){
                System.out.println("Invalid Input");
            }
        }
    }

    public void play(){
        int num = this.getNumPlayers();
        this.getPlayerNames(num);
        while (!this.checkWinner()) {
            this.model.playCurrPlayerTurn();
        }
    }

    public static void main(String[] args) {
        BoardUI game = new BoardUI();
        game.play();
    }

}
