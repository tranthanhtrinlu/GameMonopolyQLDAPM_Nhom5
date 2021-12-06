package View.Controllers;
import Model.GamePlayer.Player;
import Model.BoardElements.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Max, Kareem, Tony and Cory
 * Class HouseController for controlling the purchase and selling of houses.
 */
public class HouseController {
    private final static boolean SELL_HOUSE = false;
    private final static boolean BUY_HOUSE = true;

    /**
     * Method for buying houses.
     * @param frame A JFrame frame.
     * @param p Player, the current player
     */
    public void buyHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // always has one
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != pr.getMaxNumberOfHouses()){
                options.add(pr.getName());
            }
        }
        handlePanelOfBuyOrSellHouses(BUY_HOUSE, listProperties, options, p, frame);
    }


    /**
     * Method for selling houses.
     * @param frame A JFrame frame.
     * @param p Player, the current player
     */
    public void sellHouses(JFrame frame, Player p){
        List<Property> listProperties = p.getEstatePropertiesOfPlayer(); // always has one
        List<String> options = new ArrayList<>();
        for(Property pr: listProperties){
            if (pr.getNumOfHouses() != 0){
                options.add(pr.getName());
            }
        }
        handlePanelOfBuyOrSellHouses(SELL_HOUSE, listProperties, options, p, frame);
    }

    /**
     * create the panel for buy or sell houses accordingly, depending on the choice
     * @param choice boolean, true if buy houses otherwise false
     * @param listProperties List<Property>, a list of player properties
     * @param options List<String> list of player properties as strings respective to listProperties
     * @param p Player, the player
     * @param frame JFrame, the frame
     */
    private void handlePanelOfBuyOrSellHouses(boolean choice, List<Property> listProperties, List<String> options, Player p, JFrame frame){
        JPanel panel = new JPanel(new GridLayout(2,2));
        AtomicReference<Property> place = new AtomicReference<>(p.getPropertyByName(options.get(0)));
        JComboBox houses = new JComboBox(options.toArray());
        AtomicReference<JComboBox> num = new AtomicReference<>(new JComboBox(getBuyOrSellChoices(choice, place)));
        houses.addActionListener(e->{
            updatePanel(panel, (String) houses.getSelectedItem(), place, num, choice, p, houses);
        });
        addToPanel(panel, place, num, houses, choice);
        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Enter", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            getResultOfChoice(choice, place, num, frame, p);
        }
    }

    /**
     * method for getting the result of choice
     * @param choice boolean, true if buy houses otherwise false
     * @param place AtomicReference, Property place of player choice
     * @param num AtomicInteger, int value of player choice
     * @param p Player, the player
     * @param frame JFrame, the frame
     */
    private void getResultOfChoice(boolean choice, AtomicReference<Property> place, AtomicReference<JComboBox> num, JFrame frame, Player p){
        if (choice == BUY_HOUSE){
            place.get().addHouse((int) num.get().getSelectedItem());
            JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " purchased " + num.get().getSelectedItem() + " houses for " + place.get().getName());
            return;
        }
        place.get().sellHouse((int) num.get().getSelectedItem());
        JOptionPane.showMessageDialog(frame, "Player: " + p.getPlayerName() + " sold " + num.get().getSelectedItem() + " houses on " + place.get().getName());
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
     * @param houses A JComboBox houses.
     */
    private void updatePanel (JPanel panel, String selectedItem, AtomicReference<Property> place, AtomicReference<JComboBox> num, boolean choice, Player p, JComboBox houses){
        place.set(p.getPropertyByName(selectedItem));
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
            return getLst(place.get().numberOfHousesCanBuy()).toArray();
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
            return "<html>Number houses added to current (" + place.get().getNumOfHouses() +" of " + place.get().getMaxNumberOfHouses() + "): <br>(Cost per house is $" + place.get().getCostPerHouse() + ")</html>";
        }
        return "<html>Number of houses " + place.get().getNumOfHouses() + ": <br>(profit per house is $" + place.get().getCostPerHouse() + ")</html>";
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


