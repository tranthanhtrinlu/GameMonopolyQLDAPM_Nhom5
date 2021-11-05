package View.Controllers;

import javax.swing.*;

public class LocationController {

    public int LocationNoOwnerController(JFrame frame, String name, int cost){
        int result = JOptionPane.showConfirmDialog(
                frame,
                "You landed on " + name + ", cost is $" + cost,
                "Purchase?",
                JOptionPane.YES_NO_OPTION);
        System.out.println(result);
        return result;
    }



}
