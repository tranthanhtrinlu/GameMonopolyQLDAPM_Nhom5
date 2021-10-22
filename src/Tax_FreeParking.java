import java.util.ArrayList;
import java.util.List;

public class Tax_FreeParking extends Location{

    private int centerMoney;
    private List<Tax_FreeParkingListener> tax_freeParkingListenerList;
    public Tax_FreeParking(int cost, String name) {
        super(cost, name);
        this.centerMoney = 0;
        this.tax_freeParkingListenerList = new ArrayList<>();
    }

    public int getCenterMoney() {
        return this.centerMoney;
    }

    public void setCenterMoney(int centerMoney) {
        this.centerMoney = centerMoney;
    }

    public void addToCenterMoney(int add){
        this.centerMoney += add;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.getName().equals("FREE PARKING")){
            for (Tax_FreeParkingListener listener : this.tax_freeParkingListenerList){
                listener.FreeParking(new Tax_FreeParkingEvent(this, p));
            }
            return;
        }
        for (Tax_FreeParkingListener listener : this.tax_freeParkingListenerList){
            listener.payTax(new Tax_FreeParkingEvent(this, p));
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
