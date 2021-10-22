public class Tax_FreeParking extends Location{

    private int centerMoney;

    public Tax_FreeParking(int cost, String name) {
        super(cost, name);
        this.centerMoney = 0;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.getName().equals("FREE PARKING")){
            if (this.centerMoney == 0)
                this.centerMoney = 100;
            System.out.println("You landed on free parking, you will receive $" + this.centerMoney);
            p.setMoneyAmount(p.getMoneyAmount() + this.centerMoney);
            this.centerMoney = 0;
            return;
        }
        if (p.getMoneyAmount() <= this.getCost()){
            this.centerMoney += p.getMoneyAmount();
            return;
        }
        this.centerMoney += this.getCost();
        p.setMoneyAmount(p.getMoneyAmount() - this.getCost());
    }

    @Override
    public String toString() {
        return null;
    }
}
