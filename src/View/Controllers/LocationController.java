package View.Controllers;
import javax.swing.*;

/**
 * User input message controller for when they land on a location
 * @author Kareem El-Hajjar
 */
public class LocationController {

    /**
     * to tell the player what property they landed on and cost
     * @param frame JFrame
     * @param name String name of property
     * @param cost Integer cost of the property
     * @return frame
     */
    public int LocationNoOwnerController(JFrame frame, String name, int cost){
        return JOptionPane.showConfirmDialog(
                frame,
                "You landed on " + name + ", cost is $" + cost + "\nWould you like to purchase this property?",
                "Purchase?",
                JOptionPane.YES_NO_OPTION);
    }



}
