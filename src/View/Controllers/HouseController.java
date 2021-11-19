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
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // always has one
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != pr.getMaxNumberOfHouses()){
                options.add(pr.getName());
            }
        }
        JPanel panel = new JPanel(new GridLayout(2,2));
        AtomicReference<Property> place = new AtomicReference<>(listProperties.get(0));
        JComboBox houses = new JComboBox(options.toArray());
        AtomicReference<JComboBox> num = new AtomicReference<>(new JComboBox(getLst(place.get().getMaxNumberOfHouses() - place.get().getNumOfHouses()).toArray()));
        houses.addActionListener(e -> {
            String selectedItem = (String) houses.getSelectedItem();
            for (int i = 0; i<options.size(); i++){
                if (options.get(i).equals(selectedItem)){
                    place.set(listProperties.get(i));
                }
            }
            num.set(new JComboBox(getLst(place.get().getMaxNumberOfHouses() - place.get().getNumOfHouses()).toArray()));
            panel.removeAll();
            panel.add(new JLabel("Property: "));
            panel.add(houses);
            panel.add(new JLabel("Number houses added to current (" + place.get().getNumOfHouses() +" of " + place.get().getMaxNumberOfHouses() + "): "));
            panel.add(num.get());
            panel.revalidate();
        });
        panel.add(new JLabel("Property: "));
        panel.add(houses);
        panel.add(new JLabel("Number houses added to current (" + place.get().getNumOfHouses() +" of " + place.get().getMaxNumberOfHouses() + "): "));
        panel.add(num.get());
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            place.get().addHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " purchased " + num.get().getSelectedItem() + " houses for " + place.get().getName());
            return true;
        }
        return false;
    }


    public boolean sellHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // always has one
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != 0){
                options.add(pr.getName());
            }
        }
        JPanel panel = new JPanel(new GridLayout(2,2));
        AtomicReference<Property> place = new AtomicReference<>(listProperties.get(0));
        JComboBox houses = new JComboBox(options.toArray());
        AtomicReference<JComboBox> num = new AtomicReference<>(new JComboBox(getLst(place.get().getNumOfHouses()).toArray()));
        houses.addActionListener(e->{
            String selectedItem = (String) houses.getSelectedItem();
            for (int i = 0; i<options.size(); i++){
                if (options.get(i).equals(selectedItem)){
                    place.set(listProperties.get(i));
                }
            }
            num.set(new JComboBox(getLst(place.get().getNumOfHouses()).toArray()));
            panel.removeAll();
            panel.add(new JLabel("Property: "));
            panel.add(houses);
            panel.add(new JLabel("Number of houses " + place.get().getNumOfHouses() + ": "));
            panel.add(num.get());
            panel.revalidate();
        });
        panel.add(new JLabel("Property: "));
        panel.add(houses);
        panel.add(new JLabel("Number of houses " + place.get().getNumOfHouses() + ": "));
        panel.add(num.get());
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            place.get().sellHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " sold " + num.get().getSelectedItem() + " houses on " + place.get().getName());
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    private List<Integer> getLst(int val){
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < val; i++) {
            lst.add(i + 1);
        }
        return lst;
    }


}


