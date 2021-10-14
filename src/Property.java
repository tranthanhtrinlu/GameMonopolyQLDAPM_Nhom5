import java.util.ArrayList;
import java.util.List;

public class Property extends Location implements BoardElement{
    private List<Integer> rentCosts;
    private int numOfHouses;


    public Property(String name, int cost, int initialRent,
                    int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent){
        super(name, cost);
        this.rentCosts = new ArrayList<>(){{
           add(initialRent);
           add(house1Rent);
           add(house2Rent);
           add(house3Rent);
           add(house4Rent);
           add(hotelRent);
        }};
        this.numOfHouses = 0;
    }

    public void addHouse(){
        this.numOfHouses++;
    }

    @Override
    public void boardElementFunction() {
        // Implement later
    }

    @Override
    public String toString(){
        String s = "";
        return s;
    }

}
