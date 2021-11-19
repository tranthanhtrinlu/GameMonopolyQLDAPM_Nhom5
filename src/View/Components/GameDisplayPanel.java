package View.Components;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GameDisplayPanel for the BoardGUI
 * @author Tony Massaad
 */
public class GameDisplayPanel extends JPanel {
    private final static int DIM1 = 96;
    private final static int DIM2 = 64;
    private final static int DIM3 = 3;

    private final static int BOARD_START_TOP_X_POS = 0; // after the side panel
    private final static int BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS = DIM1; // after the top right corner image
    private final static int BOARD_START_RIGHT_X_POS = (DIM2*9) + DIM1;
    private final static int BOARD_START_BOTTOM_Y_POS = (DIM2*9) + DIM1; // 8 middle small properties width + the corner width
    private final static int[] CORNER_WIDTH_HEIGHT = new int[]{DIM1, DIM1};
    private final static int[] TOP_BOTTOM_WIDTH_HEIGHT = new int[]{DIM2, DIM1};
    private final static int[] MIDDLE_WIDTH_HEIGHT = new int[]{DIM1, DIM2};
    private final static int[] H_LINE_WIDTH_HEIGHT = new int[]{DIM1, DIM3};
    private final static int[] V_LINE_WIDTH_HEIGHT = new int[]{DIM3, DIM1};

    private final ImageIcon verticalBorder, horizontalBorder;

    private final ArrayList<JLabel> playerPieces;
    private final ArrayList<JPanel> playerPiecesDisplay;

    public GameDisplayPanel(){
        this.playerPieces = new ArrayList<>();
        this.playerPiecesDisplay = new ArrayList<>();
        Image hLine = new ImageIcon (this.getClass().getResource("/MonopolyBoardImages/BlackBorders/HORIZONTAL_BAR.png")).getImage();
        Image vLine = new ImageIcon (this.getClass().getResource("/MonopolyBoardImages/BlackBorders/VERTICAL_BAR.png")).getImage();
        hLine = hLine.getScaledInstance(H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
        vLine = vLine.getScaledInstance(V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
        this.horizontalBorder = new ImageIcon(hLine);
        this.verticalBorder = new ImageIcon(vLine);
        this.setBottomImages();
        this.setMiddleLeftImages();
        this.setTopImages();
        this.setMiddleRightImages();
        this.setLayout(null);
        this.setBackground(new Color(205, 230, 208));
    }

    /**
     * sets the starting position for images in the top
     */
    private void setTopImages(){
        int  xPos = BOARD_START_TOP_X_POS;
        ArrayList<Image> topPhotos = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/FREE_PARKING.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/RED1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/CHANCE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/RED2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/RED3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/RAILROAD3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/WATERWORKS.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/YELLOW3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/TOP/GO_TO_JAIL.png")).getImage());
        }};
        for (int i = 0; i<11; i++){
            Image location = topPhotos.get(i);
            JLabel imageLabel, border;
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            if (i % 10 == 0){ // at a corner
                location = location.getScaledInstance(CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, 0, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
                panel.setBounds(xPos, 0, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
            }
            else{
                location = location.getScaledInstance(TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, 0, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
                panel.setBounds(xPos, 0, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
            }
            xPos += imageLabel.getWidth();
            border = new JLabel(this.verticalBorder);
            border.setBounds(xPos-1, 0, V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1]);
            panel.setOpaque(false);
            this.playerPiecesDisplay.add(panel);
            this.add(panel);
            this.add(imageLabel);
            if (i != 10)
                this.add(border);
        }
    }

    /**
     * sets the starting positions for images in the middle-left
     */
    private void setMiddleLeftImages(){
        int yPos = BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS;
        int xPos = BOARD_START_TOP_X_POS;
        ArrayList<JPanel> temp = new ArrayList<>();
        ArrayList<Image> leftPhotos = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/COMMUNITY_CHEST2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ORANGE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/RAILROAD2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/ELECTRIC1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/LEFT/PINK1.png")).getImage());
        }};

        for (int i = 0; i < 9; i++){
            Image location = leftPhotos.get(i);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            location = location.getScaledInstance(MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(location));
            imageLabel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            panel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            yPos += imageLabel.getHeight();
            JLabel border = new JLabel(this.horizontalBorder);
            border.setBounds(xPos, yPos-1, H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1]);
            panel.setOpaque(false);
            temp.add(panel);
            this.add(panel);
            this.add(imageLabel);
            if (i != 8)
                this.add(border);
        }
        for (int i = temp.size()-1; i>=0; i--){
            this.playerPiecesDisplay.add(temp.get(i));
        }
    }

    /**
     * sets the starting positions for images in the bottom
     */
    private void setBottomImages(){
        int xPos = BOARD_START_TOP_X_POS;
        int yPos = BOARD_START_BOTTOM_Y_POS;
        ArrayList<JPanel> temp = new ArrayList<>();
        ArrayList<Image> bottomPhotos = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/JAIL.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/CHANCE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/LIGHT_BLUE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/RAILROAD1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/INCOME_TAX.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/BROWN2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/COMMUNITY_CHEST1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/BROWN1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/BOTTOM/GO.png")).getImage());
        }};
        for (int i = 0; i<11; i++){
            Image location = bottomPhotos.get(i);
            JLabel imageLabel, border;
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            if (i % 10 == 0){ // at a corner
                location = location.getScaledInstance(CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, yPos, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
                panel.setBounds(xPos, yPos, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
            }
            else{
                location = location.getScaledInstance(TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, yPos, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
                panel.setBounds(xPos, yPos, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
            }
            panel.setOpaque(false);
            temp.add(panel);
            this.add(panel);
            xPos += imageLabel.getWidth();
            border = new JLabel(this.verticalBorder);
            border.setBounds(xPos-1, yPos, V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1]);
            this.add(imageLabel);
            if (i != 10)
                this.add(border);
        }
        for (int i = temp.size()-1; i>=0 ; i--){
            this.playerPiecesDisplay.add(temp.get(i));
        }
    }

    /**
     * sets images in there starting positions for middle
     */
    private void setMiddleRightImages(){
        int xPos = BOARD_START_RIGHT_X_POS + BOARD_START_TOP_X_POS;
        int yPos = BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS;
        ArrayList<Image> rightPhotos = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/COMMUNITY_CHEST3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/GREEN3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/RAILROAD4.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/CHANCE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/DARK_BLUE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/LUXURY_TAX.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/MonopolyBoardImages/RIGHT/DARK_BLUE2.png")).getImage());
        }};

        for (int i = 0; i < 9; i++){
            Image location = rightPhotos.get(i);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            location = location.getScaledInstance(MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(location));
            imageLabel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            panel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            yPos += imageLabel.getHeight();
            JLabel border = new JLabel(this.horizontalBorder);
            border.setBounds(xPos, yPos-1, H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1]);
            panel.setOpaque(false);
            this.playerPiecesDisplay.add(panel);
            this.add(panel);
            this.add(imageLabel);
            if (i != 8)
                this.add(border);
        }
    }

    /**
     * add the starting players that will be playing the game
     * @param num Integer amount of players
     */
    public void addInitialPlayers(int num, int numberOfPlayers){
        String s = "P"+(num+1);
        if (num >= numberOfPlayers){
            int j = num - numberOfPlayers;
            s = "AI"+(j+1);
        }
        System.out.println(this.playerPiecesDisplay.size());
        this.playerPieces.add(new JLabel(s));
        this.playerPieces.get(num).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        this.playerPieces.get(num).setFont(new Font("Verdana", Font.BOLD, 12));
        this.playerPieces.get(num).setForeground(Color.BLACK);
        this.playerPiecesDisplay.get(0).add(this.playerPieces.get(num));
        this.playerPiecesDisplay.get(0).revalidate();
    }


    /**
     * will move the image of a players piece across the board
     * @param currentPlayer Player currently moving
     * @param oldPlayerPosition the starting position of the player
     * @param playerPosition the ending position of the player
     */
    public void movePieceImage(int currentPlayer, int oldPlayerPosition, int playerPosition){
        this.playerPiecesDisplay.get(oldPlayerPosition).remove(this.playerPieces.get(currentPlayer));
        this.playerPiecesDisplay.get(oldPlayerPosition).revalidate();
        this.playerPiecesDisplay.get(oldPlayerPosition).repaint();
        this.playerPiecesDisplay.get(playerPosition).add(this.playerPieces.get(currentPlayer));
        this.playerPiecesDisplay.get(playerPosition).revalidate();
        this.playerPiecesDisplay.get(playerPosition).repaint();
    }

    /**
     * will remove the players piece form the board
     * @param currentPlayer player that is currently being removed
     * @param playerPosition position of the player
     */
    public void removePieceFromBoard(int currentPlayer, int playerPosition){
        JLabel currentLabel = this.playerPieces.get(currentPlayer);
        this.playerPiecesDisplay.get(playerPosition).remove(currentLabel);
        this.playerPiecesDisplay.get(playerPosition).revalidate();
        this.playerPiecesDisplay.get(playerPosition).repaint();
    }

}
