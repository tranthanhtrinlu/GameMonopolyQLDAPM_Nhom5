package View.Components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    public GameDisplayPanel(){

        Image hLine = new ImageIcon (this.getClass().getResource("/MonopolyBoardImages/BlackBorders/HORIZONTAL_BAR.png")).getImage();
        Image vLine = new ImageIcon (this.getClass().getResource("/MonopolyBoardImages/BlackBorders/VERTICAL_BAR.png")).getImage();
        hLine = hLine.getScaledInstance(H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
        vLine = vLine.getScaledInstance(V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
        this.horizontalBorder = new ImageIcon(hLine);
        this.verticalBorder = new ImageIcon(vLine);
        this.setTopImages();
        this.setMiddleLeftImages();
        this.setMiddleRightImages();
        this.setBottomImages();
        this.setLayout(null);
        this.setBackground(new Color(205, 230, 208));
    }

    private void setTopImages(){
        int  xPos = BOARD_START_TOP_X_POS;
        ArrayList<Image> topPhotos = new ArrayList<Image>(){{
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
            if (i % 10 == 0){ // at a corner
                location = location.getScaledInstance(CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, 0, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
            }
            else{
                location = location.getScaledInstance(TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, 0, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
            }
            xPos += imageLabel.getWidth();
            border = new JLabel(this.verticalBorder);
            border.setBounds(xPos-1, 0, V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1]);
            this.add(imageLabel);
            if (i != 10)
                this.add(border);
        }
    }

    private void setMiddleLeftImages(){
        int yPos = BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS;
        int xPos = BOARD_START_TOP_X_POS;
        ArrayList<Image> leftPhotos = new ArrayList<Image>(){{
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
            location = location.getScaledInstance(MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(location));
            imageLabel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            yPos += imageLabel.getHeight();
            JLabel border = new JLabel(this.horizontalBorder);
            border.setBounds(xPos, yPos-1, H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1]);
            this.add(imageLabel);
            if (i != 8)
                this.add(border);
        }
    }

    private void setBottomImages(){
        int xPos = BOARD_START_TOP_X_POS;
        int yPos = BOARD_START_BOTTOM_Y_POS;
        ArrayList<Image> bottomPhotos = new ArrayList<Image>(){{
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
            if (i % 10 == 0){ // at a corner
                location = location.getScaledInstance(CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, yPos, CORNER_WIDTH_HEIGHT[0], CORNER_WIDTH_HEIGHT[1]);
            }
            else{
                location = location.getScaledInstance(TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(location));
                imageLabel.setBounds(xPos, yPos, TOP_BOTTOM_WIDTH_HEIGHT[0], TOP_BOTTOM_WIDTH_HEIGHT[1]);
            }
            xPos += imageLabel.getWidth();
            border = new JLabel(this.verticalBorder);
            border.setBounds(xPos-1, yPos, V_LINE_WIDTH_HEIGHT[0],V_LINE_WIDTH_HEIGHT[1]);
            this.add(imageLabel);
            if (i != 10)
                this.add(border);
        }
    }

    private void setMiddleRightImages(){
        int xPos = BOARD_START_RIGHT_X_POS + BOARD_START_TOP_X_POS;
        int yPos = BOARD_START_MIDDLE_LEFT_RIGHT_Y_POS;
        ArrayList<Image> rightPhotos = new ArrayList<Image>(){{
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
            location = location.getScaledInstance(MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1], Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(location));
            imageLabel.setBounds(xPos, yPos, MIDDLE_WIDTH_HEIGHT[0], MIDDLE_WIDTH_HEIGHT[1]);
            yPos += imageLabel.getHeight();
            JLabel border = new JLabel(this.horizontalBorder);
            border.setBounds(xPos, yPos-1, H_LINE_WIDTH_HEIGHT[0], H_LINE_WIDTH_HEIGHT[1]);
            this.add(imageLabel);
            if (i != 8)
                this.add(border);
        }
    }

    public void movePieceImage(){
        // Moves the piece accordingly, do some math
    }


}
