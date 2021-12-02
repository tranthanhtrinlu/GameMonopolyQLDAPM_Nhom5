package View.Components.GamePhotosComponents;
import java.awt.*;
import java.util.ArrayList;

/**
 * Interface for common implementation of the getters for board photos
 * @author Tony Massaad
 */
public interface BoardGetterFunctionality {

    /**
     * get the top images of the respective board
     * @return ArrayList<Image>, a list of top images
     */
    ArrayList<Image> getTopPhotos();

    /**
     * get the left images of the respective board
     * @return ArrayList<Image>, a list of left images
     */
    ArrayList<Image> getLeftPhotos();

    /**
     * get the bottom images of the respective board
     * @return ArrayList<Image>, a list of bottom images
     */
    ArrayList<Image> getBottomPhotos();

    /**
     * get the right images of the respective board
     * @return ArrayList<Image>, a list of right images
     */
    ArrayList<Image> getRightPhotos();

    /**
     * get the horizontal bar of the board that separates two images
     * @return Image, the horizontal bar
     */
    Image getHorizontalLine();

    /**
     * get the vertical bar of the board that separates two images
     * @return Image, the vertical bar
     */
    Image getVerticalLine();
}
