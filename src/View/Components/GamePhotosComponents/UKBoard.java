package View.Components.GamePhotosComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * class for getting the images of board version UK
 * @author Tony Massaad
 */
public class UKBoard implements BoardGetterFunctionality{

    /**
     * get the top images of the respective board
     * @return ArrayList<Image>, a list of top images
     */
    @Override
    public ArrayList<Image> getTopPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/FREE_PARKING.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/CHANCE2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RED3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/RAILROAD3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/WATERWORKS.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/YELLOW3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/TOP/GO_TO_JAIL.png"))).getImage());
        }};
    }

    /**
     * get the left images of the respective board
     * @return ArrayList<Image>, a list of left images
     */
    @Override
    public ArrayList<Image> getLeftPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/COMMUNITY_CHEST2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ORANGE1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/RAILROAD2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK3.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK2.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/ELECTRIC1.png")).getImage());
            add(new ImageIcon(this.getClass().getResource("/Monopoly_Images_UK/LEFT/PINK1.png")).getImage());
        }};
    }

    /**
     * get the bottom images of the respective board
     * @return ArrayList<Image>, a list of bottom images
     */
    @Override
    public ArrayList<Image> getBottomPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/JAIL.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE3.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/CHANCE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/LIGHT_BLUE1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/RAILROAD1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/INCOME_TAX.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/BROWN2.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/COMMUNITY_CHEST1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/BROWN1.png"))).getImage());
            add(new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BOTTOM/GO.png"))).getImage());
        }};
    }

    /**
     * get the right images of the respective board
     * @return ArrayList<Image>, a list of right images
     */
    @Override
    public ArrayList<Image> getRightPhotos(){
        return new ArrayList<>(){{
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN2.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/COMMUNITY_CHEST3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/GREEN3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/RAILROAD4.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/CHANCE3.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/DARK_BLUE1.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/LUXURY_TAX.png"))).getImage());
            add(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Monopoly_Images_UK/RIGHT/DARK_BLUE2.png"))).getImage());
        }};
    }

    /**
     * get the horizontal bar of the board that separates two images
     * @return Image, the horizontal bar
     */
    @Override
    public Image getHorizontalLine(){
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/HORIZONTAL_BAR.png"))).getImage();
    }

    /**
     * get the vertical bar of the board that separates two images
     * @return Image, the vertical bar
     */
    @Override
    public Image getVerticalLine() {
        return new ImageIcon((this.getClass().getResource("/Monopoly_Images_UK/BORDERS/VERTICAL_BAR.png"))).getImage();
    }
}
