import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
    *TO BE IMPLEMENTED AS A TEAM FOR THE GAME, BUT THE BASIS IS FOR CORY AND MAX
    *
    * Just declare and initialize everything we need:
    * List of the board element, starts with go and all the way to the second last placement on the board
    * List of players
    * method to add player, remove player
    * Getter and setters if necessary
    * play method that will start out text-based game (no need to implemment it yet)
    * main method but nothing in it
    *
    * Anything else, feel free to add.
    *
    *
 */
public class Monopoly {
    final int MIN_NUM_PLAYERS = 2;
    final int MAX_NUM_PLAYERS = 5;

    private ArrayList<Location> Locations;
    private ArrayList<Player> players;

    private int centerMoney = 0;
    private int currentPlayer = 0; // sets starting player to player 1(value will vary from 0-x-1, where x is the number of players in the game
    private int numPlayers;

    private void printWelcome(){
        System.out.println("Welcome to the game of Monopoly!");
    }

    private void getNumPlayers(){
        Scanner sc = new Scanner(System.in);
        int userInput;

        while(true){
            try{
                System.out.println("Enter number of players?(Min: " + MIN_NUM_PLAYERS + " Max: " + MAX_NUM_PLAYERS);

                userInput = sc.nextInt();
                if(userInput >= MIN_NUM_PLAYERS && userInput <= MAX_NUM_PLAYERS){
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("You did not enter an integer.");
            }
        }

        this.numPlayers = userInput;
    }

    private void getPlayerNames(){
        Scanner sc = new Scanner(System.in);

        for(int i = 0;i < numPlayers;i++){
            System.out.print("Player #"+ (i + 1) +" Enter your name: ");
            String name = sc.next();
            Player player = new Player(name);
            players.add(player);
        }

    }

    private void initBoard(){ // need to know property constructor ------------------------------------------------------------------------------------------------------DISCUSS
        //go
        Property brown1 = new Property();
        //cChest1
        Property brown2 = new Property();
        // income tax
        RailRoad rr1 = new RailRoad();
        Property lightBlue1 = new Property();
        //chance1
        Property lightBlue2 = new Property();
        Property lightBlue3 = new Property();
        //jail(just visiting)
        Property pink1 = new Property();
        //electric company
        Property pink2 = new Property();
        Property pink3 = new Property();
        RailRoad rr2 = new RailRoad();
        Property orange1 = new Property();
        //cChest2
        Property orange2 = new Property();
        Property orange3 = new Property();
        //free parking
        Property red1 = new Property();
        //chance2
        Property red2 = new Property();
        Property red3 = new Property();
        RailRoad rr3 = new RailRoad();
        Property yellow1 = new Property();
        Property yellow2 = new Property();
        //water works
        Property yellow3 = new Property();
        //go to jail
        Property green1 = new Property();
        Property green2 = new Property();
        //cChest3
        Property green3 = new Property();
        RailRoad rr4 = new RailRoad();
        //chance3
        Property darkBlue1 = new Property();
        //luxury tax
        Property darkBlue2 = new Property();

    }
    private void printBoard(){
        for(Location element : Locations){
            // to be implemented
        }
    }

    private void getCurrentPlayerAction(){
        // pass roll printPlayerState


    }

    private int roll(){ //Implement doubles? ------------------------------------------------------------------------------------------------------DISCUSS
        int rollTotal = 0;

        Random r = new Random();

        // Simulates 2 6 sided dice rolls
        for(int i = 0;i<2;i++){
            rollTotal+= r.nextInt(5) + 1;
        }

        return rollTotal;
    }

    public void play(){

        printWelcome();
        getNumPlayers();
        getPlayerNames();
        initBoard();

        // Game loop
        boolean running = true;
        while(running){
            printBoard();

            System.out.println("----------------------------------------------------------------------------");
            System.out.println(players.get(currentPlayer).getName()+ "'s turn:");
            getCurrentPlayerAction(); // DISCUSS ACTIONS WITH GROUP skip/roll------------------------------------------------------------------------------------------------------DISCUSS

        }
    }
}
