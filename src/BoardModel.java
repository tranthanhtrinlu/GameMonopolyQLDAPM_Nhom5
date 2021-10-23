import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardModel {
    public static final int SIZE_OF_BOARD = 39; // 0-39 inclusive
    public static final int GO_MONEY = 200;
    public static final int MIN_NUM_PLAYERS = 2;
    public static final int MAX_NUM_PLAYERS = 5;
    public static final int JAIL_POSITION = 10; // 11 - 1
    public final static int TOTAL_UTILITIES = 2;




    public enum Color{
        BROWN, LIGHTBLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARKBLUE
    }

    // might add an enum for location names

    private List<Location> board;
    private List<BoardView> views;
    private int currentTurn;
    private int roll1;
    private int roll2;

    public BoardModel(){
        this.board = new ArrayList<>();
        this.views = new ArrayList<>();
        this.currentTurn = 0;
        this.initializeBoard();
        this.roll1 = 0;
        this.roll2 = 0;
    }

    public void incrementCurrentTurn(){
        this.currentTurn++;
        if (this.currentTurn == this.views.size())
            this.currentTurn = 0;
    }

    private boolean rollDiceOfTwo(){
        Random r = new Random();
        this.roll1 = r.nextInt(6) +  1;
        this.roll2 = r.nextInt(6) + 1;
        return this.roll1 == this.roll2;
    }

    public void addView(BoardView view){
        this.views.add(view);
    }

    public void removePlayer(BoardView view){
        this.views.remove(view);
    }

    private void initializeBoard(){
        this.board.add(new FreePass(0, "Go"));
        this.board.add(new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250,Color.BROWN, 2));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("BALTIC AVENUE", 60,50,4,20,60,180,320,450, Color.BROWN, 2));
        this.board.add(new Tax_FreeParking(200, "INCOME TAX"));
        this.board.add(new RailRoad("Reading Railroad", 200));
        this.board.add(new Property("ORIENTAL AVENUE", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("VERMOUNT AVENUE", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3));
        this.board.add(new Property("CONNECTICUT AVENUE", 120, 50,8,40,100,300,450,600, Color.LIGHTBLUE, 3));
        this.board.add(new LandOnJail(0, "In Jail"));
        this.board.add(new Property("ST.CHARLES PLACE", 140,100,10,50,150,450,625,750, Color.PURPLE, 3));
        this.board.add(new Utility("Electric Company", 150));
        this.board.add(new Property("STATES AVENUE",140,100,10,50,150,450,625,750,Color.PURPLE, 3));
        this.board.add(new Property("Virginia Avenue", 160,100,12,60,180,500,700,900, Color.PURPLE, 3));
        this.board.add(new RailRoad("PENNSYLVANIA RAILROAD", 200));
        this.board.add(new Property("ST. JAMES PLACE", 180, 100,14,70,200,550,750,950, Color.ORANGE, 3));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("TENNESSEE AVENUE", 180,100,14,70,200,550,750,950, Color.ORANGE, 3));
        this.board.add(new Property("NEW YORK AVENUE", 200,100,16,80,220,600,800,1000, Color.ORANGE, 3));
        this.board.add(new Tax_FreeParking(0, "FREE PARKING"));
        this.board.add(new Property("KENTUCKY AVENUE",220,150,18,90,250,700,875,1050, Color.RED,3));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("INDIANA AVENUE", 220,150,18,90,250,700,875,1050, Color.RED, 3));
        this.board.add(new Property("ILLINOIS AVENUE", 240,150,20,100,300,750,925,1110, Color.RED, 3));
        this.board.add(new RailRoad("B&O Railroad", 200));
        this.board.add(new Property("ATLANTIC AVENUE", 260,150,22,110,330,800,975,1150,Color.YELLOW, 3));
        this.board.add(new Property("VENTNOR AVENUE", 260,150,22,110,330,800,975,1150, Color.YELLOW, 3));
        this.board.add(new Utility("Water Works", 150));
        this.board.add(new Property("MARVIN GARDENS", 280,150,24,120,360,850,1025,1200, Color.YELLOW, 3));
        this.board.add(new GoToJail(0, "GO TO JAIL"));
        this.board.add(new Property("PACIFIC AVENUE", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3));
        this.board.add(new Property("NORTH CAROLINA AVENUE", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("PENNYSYLVANIA AVENUE", 320,200,28,150,450,1000,1200,1400, Color.GREEN, 3));
        this.board.add(new RailRoad("Short Line", 200));
        this.board.add(new FreePass(0, "Free Pass"));
        this.board.add(new Property("PARK PLACE", 350,200,35,175,500,1100,1300,1500,Color.DARKBLUE, 2));
        this.board.add(new Tax_FreeParking(100, "LUXURY TAX"));
        this.board.add(new Property("BOARDWALK", 400,200,50,200,600,1400,1700,200, Color.DARKBLUE, 3));
    }

    public void addViewToListener(BoardView view){
        for (Location location : this.board){
            location.addListener(view);
        }
    }

    public void announceReachingGo(){
        for (BoardView view : this.views){
            view.announceReachingGo();
        }
    }

    public void announceLandedResult(BoardEvent e, boolean result){
        for (BoardView view : this.views){
            view.handleGameStatus(e, result);
        }
    }

    public void announceLanded(BoardEvent e) {
        for (BoardView view: this.views){
            view.handleAnnounceLanded(e);
        }

    }

    public void announcePropertyResult(BoardEvent e) {
        for (BoardView view: this.views){
            view.handlePurchaseAnnouncment(e);
        }
    }

    public void playCurrPlayerTurn(){
        BoardView currView = this.views.get(this.currentTurn);
        boolean doubles = rollDiceOfTwo();
        BoardEvent e = new BoardEvent(this, this.board, doubles, this.roll1, this.roll2);
        boolean hasProperty = currView.checkIfPlayerHasProperties(e);
        int choice = currView.handleCurrentPlayerChoice(e);

        if (hasProperty){
            if (choice == 1) {
                currView.handlePrintStateOfEachPlayer(e);
            }
            else if (choice == 2){
                currView.handlePlayerMovement(e);
                if (!doubles){
                    this.incrementCurrentTurn();
                    currView.handleNextTurn(e);
                }
            }
            else if (choice == 3){
                for (BoardView view : this.views){
                    view.announceDecisionToPurchaseHouses(e);
                }
                currView.handlePlayerChoiceToPurchaseHouses(e);
            }
            else if (choice == 4){
                for (BoardView view : this.views){
                    view.announcePlayerPass(e);
                }
                currView.handleNextTurn(e);
            }
            else if (choice == 5){
                for (BoardView view : this.views){
                    view.handlePlayerQuit(e);
                }
                this.incrementCurrentTurn();
                currView.handleNextTurn(e);
            }
        }
        else{
            if (choice == 1){
                currView.handlePrintStateOfEachPlayer(e);
            }
            else if (choice == 2){
                currView.handlePlayerMovement(e);
                if (!doubles){
                    this.incrementCurrentTurn();
                    currView.handleNextTurn(e);
                }
            }
            else if (choice == 3){
                for (BoardView view : this.views){
                    view.announcePlayerPass(e);
                }
                currView.handleNextTurn(e);
            }
            else if (choice == 4){
                for (BoardView view : this.views){
                    view.handlePlayerQuit(e);
                }
                this.incrementCurrentTurn();
                currView.handleNextTurn(e);
            }
        }
    }
}
