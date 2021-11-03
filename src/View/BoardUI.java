package View;
import Events.*;
import Listener.BoardView;
import Model.BoardModel;
import Model.Location;
import Model.Player;
import Model.Property;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Max Curkovic, Tony Massaad, Cory Helm, Kareem El-Hajjar (this class was a collective effort based on how long it would be)
 * Class MVC.BoardUI that implements the UI of the board.
 */
public class BoardUI implements BoardView {

    protected BoardModel model;
    private List<Player> gamePlayers;
    private int currentTurn;
    private boolean pass, purchasesProperty, purchaseHouses;
    private final Scanner sc;
    private int userInput;

    /**
     * Default constructor for MVC.BoardUI.
     */
    public BoardUI(){
        this.sc = new Scanner(System.in);
        this.gamePlayers = new ArrayList<>();
        this.model = new BoardModel();
        this.model.addView(this);
        this.model.addViewToListener(this);
        this.currentTurn = 0;
        this.pass = false;
        this.purchasesProperty = false;
        this.purchaseHouses = false;
    }

    /**
     * Method for getting the number of players in the game.
     * @return An integer number of players.
     */
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
                this.sc.next();
            }
        }
        return this.userInput;
    }

    /**
     * Method for getting the player names.
     * @param numPlayers An integer numPlayers.
     */
    private void getPlayerNames(int numPlayers){
        for(int i = 0;i <numPlayers;i++){
            System.out.print("Player #"+ (i + 1) +" Enter your name: ");
            String name = this.sc.next();
            this.gamePlayers.add(new Player(name));
        }
    }

    /**
     * Method for handling a bankrupted player.
     * @param p A MVC.Player object p.
     */
    private void bankruptedPlayer(Player p){
        System.out.println("Player: " + p.getPlayerName() + " has no more money. Removing player from game.");
        System.out.println("Player properties are now back in estate!");
        p.bankrupted();
        this.gamePlayers.remove(p);
    }

    /**
     * Method for checking if a player has won the game.
     * @return True if there is only one player left in the game, false otherwise.
     */
    public boolean checkWinner(){
        if (this.gamePlayers.size() == 1){
            System.out.println("Winner is " + this.gamePlayers.get(0).getPlayerName() + ", Ending game now");
            return true;
        }
        return false;
    }

    //****BEGINNING OF PROPERTY FUNCTIONS**//

    /**
     * Overridden method to handle a property with no owner. A player can either pass on or buy a property.
     * If bought, give player choices to purchase houses.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyNoOwner(PropertyEvent e) {
        while(true){
            try{
                System.out.println("You landed on " + e.getProperty().getName() + " Cost is " + e.getProperty().getCost());
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                System.out.print("<option number> ");
                this.userInput = this.sc.nextInt();
                if(this.userInput < 0 || this.userInput > 2){
                    System.out.println("invalid input");
                    continue;
                }
                break;
            }catch(InputMismatchException exception){
                System.out.println("Invalid");
                this.sc.next();
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
            boolean choice = true;
            while (true) {
                try{
                    while(choice){
                        try{
                            System.out.println("Would you want to buy any houses? (Cost per house is $ " + e.getProperty().getCostPerHouse() + ")" + "\n(1) YES \n(2) No");
                            System.out.print("<Option number> ");
                            this.userInput = this.sc.nextInt();
                            if (this.userInput < 0 || this.userInput > 2){
                                System.out.println("Invalid Input");
                                continue;
                            }
                            if (this.userInput == 1){
                                buy = true;
                            }
                            choice = false;
                        }catch (InputMismatchException exception){
                            System.out.println("Invalid number");
                            this.sc.next();
                        }
                    }
                    if (!buy){
                        break;
                    }
                    System.out.println("Each house cost " + e.getProperty().getCostPerHouse());
                    System.out.println("Out of " + e.getProperty().getMaxNumberOfHouses() + ", how many would you like to buy?");
                    System.out.print("<Input number> ");
                    this.userInput = this.sc.nextInt();
                    if(!e.getProperty().addHouse(this.userInput, e.getPlayer())){
                        System.out.println("invalid amount of houses or not enough money");
                        continue;
                    }
                    break;
                }catch(InputMismatchException exception){
                    System.out.println("Invalid Input");
                    this.sc.next();
                }
            }
        }
        else{
            this.pass = true;
        }
    }

    /**
     * Overridden method to handle a property already owned by a player.
     * Can either buy houses/hotels or pass.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyOwned(PropertyEvent e) {
        if (e.getProperty().getNumOfHouses() != e.getProperty().getMaxNumberOfHouses()){
            while(true){
                try{
                    System.out.println("You landed on " + e.getProperty().getName() + ", Property you own");
                    System.out.println("Would you want to \n(1) purchase some buildings \n(2) pass?");
                    System.out.print("<Option number> ");
                    this.userInput = this.sc.nextInt();
                    if(this.userInput < 0 || this.userInput > 2){
                        System.out.println("invalid input");
                        continue;
                    }
                    break;
                }catch(InputMismatchException exception){
                    System.out.println("Invalid input");
                    this.sc.next();
                }
            }
            if (this.userInput == 1){
                while(true){
                    try{
                        System.out.println("Each house cost " + e.getProperty().getCostPerHouse());
                        System.out.println("You currently have " + e.getProperty().getNumOfHouses() + " of " + e.getProperty().getMaxNumberOfHouses());
                        System.out.println("How many would you like to currently buy?");
                        System.out.print("<Amount> ");
                        this.userInput = this.sc.nextInt();
                        if(!e.getProperty().addHouse(this.userInput, e.getPlayer())){
                            System.out.println("invalid amount or not enough money");
                            continue;
                        }
                        break;
                    }catch(InputMismatchException exception){
                        System.out.println("Invalid input");
                        this.sc.next();
                    }
                }
            }
            else{
                this.pass = true;
            }
        }else{
            System.out.println("You landed on " + e.getProperty().getName() + ", Property you own");
            System.out.println("You maxed out on houses already, moving to next player");
            this.pass = true;
        }
    }

    /**
     * Overridden method to handle a property owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyRent(PropertyEvent e) {
        Player owner = e.getProperty().getOwner();
        Player landedPlayer = e.getPlayer();
        System.out.println("You landed on " + e.getProperty().getName() + " Owned by " + owner.getPlayerName() + " and rent is $" + e.getProperty().getRent());
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

    /**
     * Overridden method for displaying the landed property result based on a board event.
     * @param e A Events.PropertyEvent e.
     * @param event A Events.BoardEvent event.
     */
    @Override
    public void displayLandedPropertyResult(PropertyEvent e, BoardEvent event) {
        event.getModel().announcePropertyResult(event);
    }
    // ** ENDING OF PROPERTY IMPLEMENTATION

    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//

    /**
     * Overridden method to handle a railroad with no owner. A player can either pass on or buy a property.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadNoOwner(RailRoadEvent e) {
        while(true) {
            try {
                System.out.println("You landed on " + e.getRailRoad().getName() + " Cost is " + e.getRailRoad().getCost());
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                System.out.print("<Option number> ");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput > 2) {
                    System.out.println("invalid input");
                    continue;
                }
                break;
            } catch (InputMismatchException exception) {
                System.out.println("invalid input");
                this.sc.next();
            }
        }
        if (userInput == 1){
            if (e.getRailRoad().buy(e.getPlayer())){
                this.pass = true;
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
            }
        }
        else{
            this.pass = true;
        }
    }

    /**
     * Method to handle a railroad that a player who owns it lands on.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadOwned(RailRoadEvent e) {
        System.out.println("You landed on " + e.getRailRoad().getName() + ", Which you own");
    }

    /**
     * Overridden method to handle a railroad owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadRent(RailRoadEvent e) {
        Player owner = e.getRailRoad().getOwner();
        System.out.println("You landed on " + e.getRailRoad().getName() + " Owned by " + owner.getPlayerName() + " and payment is $" + e.getRailRoad().getPayment());
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

    /**
     * Overridden method for displaying the landed railroad result based on a board event.
     * @param e A Events.RailRoadEvent e.
     * @param boardEvent A Events.BoardEvent event.
     */
    @Override
    public void displayLandedRailroadResult(RailRoadEvent e, BoardEvent boardEvent) {
        boardEvent.getModel().announcePropertyResult(boardEvent);
    }
    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    /**
     * Overridden method to handle a utility with no owner. A player can either pass on or buy a property.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityNoOwner(UtilityEvent e) {
        while(true) {
            try {
                System.out.println("You landed on " + e.getUtility().getName() + " Cost is " + e.getUtility().getCost());
                System.out.println("Would you want to \n(1) purchase \n(2) pass?");
                System.out.print("<Option number> ");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput > 2) {
                    System.out.println("invalid input");
                    continue;
                }
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input");
                this.sc.next();
            }
        }
        if (this.userInput == 1){
            if (e.getUtility().buy(e.getPlayer())){
                this.pass = true;
                System.out.println("Sorry, you don't have enough money. Moving to the next player");
            }
        }
        else{
            this.pass = true;
        }
    }

    /**
     * Method to handle a utility that a player who owns it lands on.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityOwned(UtilityEvent e) {
        System.out.println("You landed on " + e.getUtility().getName() + ", Which you own");
    }

    /**
     * Overridden method to handle a utility owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityPay(UtilityEvent e) {
        Player owner = e.getUtility().getOwner();
        System.out.println("You landed on " + e.getUtility().getName() + " Owned by " + owner.getPlayerName() + ".\n" +
                "Number of utilities owned is " + owner.getNumOfUtilities() + "Payment (dice roll * (10 if 2 utilities else 4)) is $" + e.getUtility().payment(e.getTotalDiceRoll()));
        System.out.println("You pay now");
        int landedPlayerMoney = e.getPlayer().getMoneyAmount();
        int payment = e.getUtility().payment(e.getTotalDiceRoll());
        if (landedPlayerMoney <= payment){
            owner.setMoneyAmount(owner.getMoneyAmount() + landedPlayerMoney);
            this.bankruptedPlayer(e.getPlayer());
            return;
        }
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - payment);
        owner.setMoneyAmount(e.getPlayer().getMoneyAmount() + payment);
    }

    /**
     * Overridden method for displaying the landed utility result based on a board event.
     * @param e A Events.UtilityEvent e.
     * @param boardEvent A Events.BoardEvent event.
     */
    @Override
    public void displayLandedUtilityResult(UtilityEvent e, BoardEvent boardEvent) {
        boardEvent.getModel().announcePropertyResult(boardEvent);
    }
    // ** END OF UTILITY IMPLEMENTATION **

    /**
     * Overridden method for handling free parking.
     * If a player lands here they get the money in the "center" of the board.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void FreeParking(Tax_FreeParkingEvent e) {
        if (e.getLocation().getCenterMoney() == 0)
            e.getLocation().setCenterMoney(100);
        System.out.println("You landed on free parking, you will receive $" + e.getLocation().getCenterMoney());
        e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() + e.getLocation().getCenterMoney());
        e.getLocation().setCenterMoney(0);
    }

    /**
     * Overridden method for handling the payment of tax.
     * If player runs out of money, they will go bankrupt.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
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

    /**
     * Overridden method for handling "Just Visiting".
     * @param e A Events.LandOnJailEvent event.
     */
    @Override
    public void visiting(LandOnJailEvent e) {
        System.out.println("You landed " + e.getLandOnJail().getName() + " - Just Visiting");
        System.out.println("You are just Visiting, moving to the next player");
    }

    // END OF IMPLEMENTING TAX AND PARKING //

    // BEGINNING OF IMPLEMENTING LAND ON JAIL //

    /**
     * Overridden method for sending a player to jail.
     * @param e Events.GoToJailEvent e.
     */
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        System.out.println("You landed on " + e.getGoToJail().getName() + " so you going to jail.");
        e.getPlayer().setPosition(BoardModel.JAIL_POSITION);
        e.getPlayer().setInJail(true);
    }
    // ENDING OF LAND ON JAIL //

    /**
     * Overridden method for handling a free pass. These replace the Chance + Community Chest as placeholders on the board.
     * @param e Events.FreePassEvent event.
     */
    @Override
    public void FreePass(FreePassEvent e) {
        System.out.println("You landed on a + " + e.getPass().getName() + " Enjoy it!");
    }

    /**
     * Overridden method to check if the player has properties.
     * @param e A Events.BoardEvent e.
     * @return True if the player has properties, false otherwise.
     */
    // HANDLES
    @Override
    public boolean checkIfPlayerHasProperties(BoardEvent e){
        return this.gamePlayers.get(this.currentTurn).numberOfEstateProperties() != 0;
    }

    /**
     * Overridden method for handling the print states of each player based on a board event.
     * @param e A Events.BoardEvent e.
     */

    @Override
    public void handlePrintStateOfEachPlayer(BoardEvent e) {
        for (Player p : this.gamePlayers){
            System.out.println(p.toString());
        }
    }

    /**
     * Overridden method that announces when a player has reached GO!.
     */
    @Override
    public void announceReachingGo() {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println(p.getPlayerName() + " received $" + BoardModel.GO_MONEY + " for reaching GO");
    }

    /**
     * Overridden method for handling the game status.
     * @param e A Events.BoardEvent e.
     * @param result A boolean result.
     */
    @Override
    public void handleGameStatus(BoardEvent e, boolean result) {
        if (result){
            e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).getResult(this.gamePlayers.get(this.currentTurn), e);
        }
    }

    /**
     * Overridden method for handling the announcement of an element landed on.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handleAnnounceLanded(BoardEvent e){
        Player p = this.gamePlayers.get(this.currentTurn);
        Location place = e.boardElement(p.getPosition());
        System.out.println(p.getPlayerName() + " landed on " + place.toString(p));
    }

    /**
     * Overridden method for handling the purchase announcement of a board element.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePurchaseAnnouncment(BoardEvent e) {
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        Player player = this.gamePlayers.get(this.currentTurn);
        if (this.pass){
            System.out.println("Player: " + player.getPlayerName() + " has done nothing. Moving to the next player");
            this.pass = false;
            return;
        }

        if (this.purchaseHouses){
            System.out.println("Player: " + player.getPlayerName() + " purchased " + (((Property) place).getNumOfHouses() - ((Property) place).getOldNumOfHouses()) + " for " + place.getName());
            this.purchaseHouses = false;
            return;
        }

        if (place instanceof Property){
            if (this.purchasesProperty){
                System.out.println("Player: " + player.getPlayerName() + " has purchase the property " + place.getName() + " and owns " + ((Property) place).getNumOfHouses() + " houses");
                this.purchasesProperty = false;
                return;
            }
            System.out.println("Player: " + player.getPlayerName() + " has purchase " + (((Property) place).getNumOfHouses() - ((Property) place).getOldNumOfHouses()) + " Houses for " + place.getName());
            return;
        }
        System.out.println("Player: " + player.getPlayerName() + " has purchased " + place.getName());
    }

    /**
     * Overridden method for handling the next turn of the player.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handleNextTurn(BoardEvent e) {
        this.currentTurn++;
        if (this.currentTurn == this.gamePlayers.size())
            this.currentTurn = 0;
    }

    /**
     * Overridden method for handling the announcement of a player passing the turn.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announcePlayerPass(BoardEvent e) {
        Player p = this.gamePlayers.get(currentTurn);
        System.out.println("Player " + p.getPlayerName() + " passed the turn. Moving to the next player.");
    }

    /**
     * Overridden method for handling the player quit.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerQuit(BoardEvent e) {
        Player quittingPlayer = this.gamePlayers.get(currentTurn);
        System.out.println("Player: " + quittingPlayer.getPlayerName() + " has quit the game");
        quittingPlayer.bankrupted();
        this.gamePlayers.remove(quittingPlayer);
    }

    /**
     * Overridden method for handling the player's choice to purchase houses or not.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerChoiceToPurchaseHouses(BoardEvent e) {
        Player p = this.gamePlayers.get(this.currentTurn);
        while (true){
            try{
                System.out.println("Which property would you like to purchase houses for?");
                List<Property> estates = p.getEstatePropertiesOfPlayer();
                int k = 0;
                for (Property owned : estates){
                    System.out.println("(" + k + ") " + owned.getName() + ", number of houses: " + owned.getNumOfHouses());
                }
                System.out.print("<number input>>> ");
                this.userInput = this.sc.nextInt();
                if (this.userInput < 0 || this.userInput >= estates.size()){
                    System.out.println("Invalid option");
                    continue;
                }
                Property place = p.getPropertyByName(estates.get(this.userInput).getName());
                if (place.getNumOfHouses() != place.getMaxNumberOfHouses()){
                    while(true){
                        try{
                            System.out.println("Each house cost " + place.getCostPerHouse());
                            System.out.println("You currently have " + place.getNumOfHouses() + " of " + place.getMaxNumberOfHouses());
                            System.out.println("How many would you like to currently buy?");
                            this.userInput = this.sc.nextInt();
                            if(!place.addHouse(this.userInput, p)){
                                System.out.println("invalid amount or not enough money");
                                continue;
                            }
                            this.purchaseHouses = true;
                            e.getModel().announcePropertyResult(e);
                            break;
                        }catch(InputMismatchException exception){
                            System.out.println("You did not enter an integer.");
                            this.sc.next();
                        }
                    }
                    return;
                }
                System.out.println("You maxed out on houses already");
            }catch(InputMismatchException exception){
                System.out.println("Invalid input");
                this.sc.next();
            }
        }
    }

    /**
     * Overridden method to handle the announcement of the decision of the player to purchase houses.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announceDecisionToPurchaseHouses(BoardEvent e) {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println("Player: " + p.getPlayerName() + " has decided to purchase houses for a property they own");
    }

    /**
     * Overridden method to handle player movement in general, depending on where the player is located.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerMovement(BoardEvent e) {
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println("Player: " + p.getPlayerName() + " rolled: ( " + e.getRoll1() + " + " + e.getRoll2() + " = " + e.diceSum() + ")");
        if (!p.getInJail()){
            if (this.gamePlayers.get(this.currentTurn).movePlayer(e.diceSum())){
                e.getModel().announceReachingGo();
            }
            e.getModel().announceLanded(e);
            if (place.getName().equals("In Jail") && !p.getInJail()){
                p.setCurrLocation(place.getName() + ", Just visiting");
                boolean result = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).locationElementFunctionality(this.gamePlayers.get(this.currentTurn), e.diceSum());
                e.getModel().announceLandedResult(e, result);
                return;
            }
            this.gamePlayers.get(this.currentTurn).setCurrLocation(e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).getName());
            boolean result = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition()).locationElementFunctionality(this.gamePlayers.get(this.currentTurn), e.diceSum());
            e.getModel().announceLandedResult(e, result);
        }
        else{
            System.out.println("You have " + p.getTurnsInJail() + " more turns in jail");
            if (e.getDoubles()){
                System.out.println("Nice You rolled a double! You are now Just Visiting Jail");
                p.setInJail(false);
                p.setCurrLocation(place.getName() + ", Just visiting");
                e.getModel().announceRolledOutOfJail();
                return;
            }
            p.setTurnsInJail(p.getTurnsInJail() - 1);
            if (p.getTurnsInJail() != 0){
                System.out.println("You didn't roll a double. You still have " + p.getTurnsInJail());
                e.getModel().announceDidNotRollOutOfJail();
            }
            else{
                System.out.println("You are now out of turns in jail, you have to pay the $50");
                p.setMoneyAmount(p.getMoneyAmount() - 50);
                e.getModel().announcePaidOutOfJail(e, false);
            }
        }

    }

    /**
     * Overridden boolean method for handling the payment in jail.
     * @param e A Events.BoardEvent e.
     * @return True if player has paid the jail fee, false otherwise.
     */
    @Override
    public boolean payJail(BoardEvent e){
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        if (this.gamePlayers.get(this.currentTurn).payJail()){
            System.out.println("You are out of jail and now just visiting");
            e.getModel().announcePaidOutOfJail(e, true);
            return true;
        }
        System.out.println("Not enough money to get out of jail");
        return false;
    }

    /**
     * Overridden method for handling the announcement of rolling out of jail.
     */
    @Override
    public void handleAnnounceRolledOutOfJail() {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println("Player: " + p.getPlayerName() + " Rolled out of jail and is now just visiting!");
    }

    /**
     * Overridden method for handling the announcement of not rolling out of jail.
     */
    @Override
    public void handleAnnounceDidNotRollOutOfJail() {
        Player p = this.gamePlayers.get(this.currentTurn);
        System.out.println("Player: " + p.getPlayerName() + " Failed to rolled out of jail!");
    }

    /**
     * Overridden method for handling the announcement of the player posting bail.
     * @param e A Events.BoardEvent e.
     * @param b A boolean b.
     */
    @Override
    public void handleAnnouncePayedOutOfJail(BoardEvent e, boolean b) {
        Player p = this.gamePlayers.get(this.currentTurn);
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        if (!b){
            System.out.println("Player: " + p.getPlayerName() + " Failed to rolled out of jail for 3 turns!" +
                    "\nPlayer is losing $50 to now visit jail");
        }
        else{
            System.out.println("Player payed the $50 to get out of jail and now just visiting");
        }
        p.setInJail(false);
        p.setCurrLocation(place.getName() + ", Just visiting");

    }

    /**
     * Overridden boolean method for updating the game players if one loses the game or quits.
     * @param e A Events.BoardEvent e.
     * @return True if the game players is updated, false otherwise.
     */
    @Override
    public boolean updateGamePlayers(BoardEvent e) {
        Player p = this.gamePlayers.get(this.currentTurn);
        if (p.getMoneyAmount() == 0){
            System.out.println("Player: " + p.getPlayerName() + " is out of money and is now removed from the game!");
            this.gamePlayers.remove(this.currentTurn);
            return true;
        }
        return false;
    }

    /**
     * Overridden method for announcing the current player.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announceCurrentPlayer(BoardEvent e){
        System.out.println("Current Player: " + this.gamePlayers.get(this.currentTurn).getPlayerName());
    }

    /**
     * Overridden boolean method for checking if the player is in jail.
     * @param e A Events.BoardEvent e.
     * @return True if the player is in jail, false otherwise.
     */
    @Override
    public boolean checkIfPlayerInJail(BoardEvent e) {
        return this.gamePlayers.get(this.currentTurn).getInJail();
    }

    /**
     * Overriden integer method for handling all the player choices that they could potentially choose.
     * @param e A Events.BoardEvent object e.
     * @return An integer player choice.
     */
    @Override
    public int handleCurrentPlayerChoice(BoardEvent e) {
        int numOfOptions;

        numOfOptions = 4;
        if (this.checkIfPlayerHasProperties(e)){
            numOfOptions = 5;
        }

        if (this.gamePlayers.get(this.currentTurn).getInJail()){
            numOfOptions = 2;
        }

        while (true){
            try{
                e.getModel().announceCurrentPlayer(e);
                System.out.println("You are currently at " + e.boardElementName(this.gamePlayers.get(this.currentTurn).getPosition()));
                if (numOfOptions == 5){
                    System.out.println("What would you like to do?");
                    System.out.println("(1) Print State of Each Player");
                    System.out.println("(2) Roll");
                    System.out.println("(3) Purchase house for an existing property?");
                    System.out.println("(4) Pass");
                    System.out.println("(5) Quit");
                }
                else if (numOfOptions == 4){
                    System.out.println("What would you like to do?");
                    System.out.println("(1) Print State of Each Player");
                    System.out.println("(2) Roll");
                    System.out.println("(3) Pass");
                    System.out.println("(4) Quit");
                }
                else {
                    System.out.println("You can either,");
                    System.out.println("(1) Roll doubles to get out of jail");
                    System.out.println("(2) Pay $50 to get out of jail");
                }
                System.out.print("<Option number> ");
                this.userInput = sc.nextInt();
                if (this.userInput > 0 && this.userInput <= numOfOptions){
                    return this.userInput;
                }
                System.out.println("Invalid option");
            }catch(InputMismatchException exception){
                System.out.println("Invalid Input");
                this.sc.next();
            }
        }
    }

    /**
     * Method for playing the game.
     */
    public void play(){
        int num = this.getNumPlayers();
        this.getPlayerNames(num);
        while (!this.checkWinner()) {
            this.model.playCurrPlayerTurn();
            System.out.println();
        }
    }

    /**
     * Main method for running the game.
     * @param args A String Array of args.
     */
    public static void main(String[] args) {
        BoardUI game = new BoardUI();
        game.play();
    }

}
