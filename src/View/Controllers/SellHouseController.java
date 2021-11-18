package View.Controllers;

import Model.Player;
import Model.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Curkovic
 * Class SellHouseController for controlling the selling of a player's property's houses.
 */
public class SellHouseController {

    int numHousesSelected;

    public void numberHouses(JFrame frame){
        Integer[] options = {1,2,3,4};
        numHousesSelected = (Integer)JOptionPane.showInputDialog(frame, "Pick a number of houses to sell.", "Number of Houses", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public void propertiesHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer();
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if(pr.getNumOfHouses() >= numHousesSelected){
                options.add(pr.getName());
            }
        }
        Object[] options2 = options.toArray();
        Property a = (Property)JOptionPane.showInputDialog(frame, "Pick a property to sell houses for.", "Select a Property", JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
        for (Property pr: listProperties){
            if (pr.getName().equals(a.getName())){
                p.setMoneyAmount(p.getMoneyAmount() + 50);
                pr.setNumOfHouses(pr.getOldNumOfHouses());
                break;
            }
        }
    }

}