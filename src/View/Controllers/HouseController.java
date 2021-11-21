package View.Controllers;
import Model.GamePlayer.Player;
import Model.BoardElements.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Max and Kareem
 * Class HouseController for controlling the purchase and selling of houses.
 */
public class HouseController {


    private final static boolean SELL_HOUSE = false;
    private final static boolean BUY_HOUSE = true;

    /**
     * Method for buying houses.
     * @param frame A JFrame frame.
     * @param p
     */
    public void buyHouses(JFrame frame, Player p){
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
            updatePanel(panel, (String) houses.getSelectedItem(), place, num, BUY_HOUSE, options,listProperties, houses);
        });
        addToPanel(panel, place, num, houses, BUY_HOUSE);
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            place.get().addHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " purchased " + num.get().getSelectedItem() + " houses for " + place.get().getName());
        }
    }


    /**
     * Method for selling houses.
     * @param frame A JFrame frame.
     * @param p A Player p
     */
    public void sellHouses(JFrame frame, Player p){
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
            updatePanel(panel, (String) houses.getSelectedItem(), place, num, SELL_HOUSE, options,listProperties, houses);
        });
        addToPanel(panel, place, num, houses, SELL_HOUSE);
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            place.get().sellHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " sold " + num.get().getSelectedItem() + " houses on " + place.get().getName());
        }
    }

    /**
     * Method for getting the list of integer choices for buying and selling houses
     * @return A List of integers lst.
     */
    private List<Integer> getLst(int val){
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < val; i++) {
            lst.add(i + 1);
        }
        return lst;
    }

    /**
     * Method for updating the panel.
     * @param panel A JPanel panel.
     * @param selectedItem A String selectedItem.
     * @param place An Atomic reference to a Property place.
     * @param num An Atomic reference to a JComboBox num.
     * @param choice A Boolean choice.
     * @param options A List of String choices.
     * @param listProperties A List of property properties.
     * @param houses A JComboBox houses.
     */
    private void updatePanel (JPanel panel, String selectedItem, AtomicReference<Property> place, AtomicReference<JComboBox> num, boolean choice, List<String> options, List<Property> listProperties, JComboBox houses){
        for (int i = 0; i<options.size(); i++){
            if (options.get(i).equals(selectedItem)){
                place.set(listProperties.get(i));
            }
        }
        num.set(new JComboBox(getBuyOrSellChoices(choice, place)));
        panel.removeAll();
        panel.add(new JLabel("Property: "));
        panel.add(houses);
        panel.add(new JLabel(getText(choice, place)));
        panel.add(num.get());
        panel.revalidate();
    }

    /**
     * Method for getting the buy or sell choices of the player.
     * @param choice A boolean choice.
     * @param place An atomic reference to a property place.
     * @return A List of choices.
     */
    private Object[] getBuyOrSellChoices(boolean choice, AtomicReference<Property> place){
        if(choice == BUY_HOUSE){
            return getLst(place.get().getMaxNumberOfHouses() - place.get().getNumOfHouses()).toArray();
        }
        return getLst(place.get().getNumOfHouses()).toArray();
    }


    /**
     * Method for returning a get text string.
     * @param choice A Boolean choice.
     * @param place An atomic reference to a property place.
     * @return A string statement.
     */
    private String getText(boolean choice, AtomicReference<Property> place){
        if(choice == BUY_HOUSE){
            return "Number houses added to current (" + place.get().getNumOfHouses() +" of " + place.get().getMaxNumberOfHouses() + "): ";
        }
        return "Number of houses " + place.get().getNumOfHouses() + ": ";

    }

    /**
     * Method for adding to the panel.
     * @param panel A JPanel panel.
     * @param place An atomic reference to a property place.
     * @param num An atomic reference to a JComboBox num.
     * @param houses A JComboBox houses.
     * @param choice A boolean choice.
     */
    private void addToPanel(JPanel panel, AtomicReference<Property> place, AtomicReference<JComboBox> num, JComboBox houses, boolean choice){
        panel.add(new JLabel("Property: "));
        panel.add(houses);
        panel.add(new JLabel(getText(choice, place)));
        panel.add(num.get());
    }

}


