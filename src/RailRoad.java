import java.util.ArrayList;
import java.util.List;

public class RailRoad extends Location implements BoardElement{
    private List<Integer> payments;

    public RailRoad(String name, int cost){
        super(name, cost);
        this.payments = new ArrayList<>(){{
           add(25);
           add(50);
           add(100);
           add(200);
        }};
    }

    @Override
    public void boardElementFunction() {

    }

    @Override
    public String toString(){
        return "";
    }
}
