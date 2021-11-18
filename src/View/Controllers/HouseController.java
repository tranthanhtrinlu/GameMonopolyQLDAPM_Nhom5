package View.Controllers;
import Model.Player;
import Model.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Max and Kareem
 */
public class HouseController {

    public boolean buyHouses(JFrame frame, Player p){
        JPanel panel = new JPanel(new GridLayout(2,2));
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // always has one
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            options.add(pr.getName());
        }
        AtomicReference<Property> place = new AtomicReference<>(listProperties.get(0));
        JComboBox houses = new JComboBox(options.toArray());
        AtomicReference<JComboBox> num = new AtomicReference<>(new JComboBox(getLst(place.get()).toArray()));
        houses.addActionListener(e->{
            String selectedItem = (String) houses.getSelectedItem();
            for (int i = 0; i<options.size(); i++){
                if (options.get(i).equals(selectedItem)){
                    place.set(listProperties.get(i));
                }
            }
            num.set(new JComboBox(getLst(place.get()).toArray()));
        });
        panel.add(new JLabel("Property: "));
        panel.add(houses);
        panel.add(new JLabel("Number houses added to current: "));
        panel.add(num.get());
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            place.get().addHouse((int) num.get().getSelectedItem());
            return true;
        }
        return false;
    }


    public boolean sellHouses(JFrame frame, Player p){
        return false;
    }


    /**
     *
     * @param place
     * @return
     */
    private List<Integer> getLst(Property place){
        int totalHousesCanBuy = place.getMaxNumberOfHouses() - place.getNumOfHouses();
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < totalHousesCanBuy; i++) {
            lst.add(i + 1);
        }
        return lst;
    }


}


