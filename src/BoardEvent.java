import java.util.EventObject;
import java.util.List;

public class BoardEvent extends EventObject {

    private List<Location> board;
    private boolean doubles;
    private int roll1;
    private int roll2;
    public BoardEvent(BoardModel boardModel, List<Location> board, boolean doubles, int roll1, int roll2) {
        super(boardModel);
        this.board = board;
        this.doubles = doubles;
        this.roll1 = roll1;
        this.roll2 = roll2;
    }

    public int getRoll1() {
        return this.roll1;
    }

    public int getRoll2() {
        return this.roll2;
    }

    public int diceSum(){
        return this.roll1 + this.roll2;
    }

    public boolean getDoubles(){
        return this.doubles;
    }

    public String boardElementName(int index){
        if (index < 0 || index > this.board.size()){
            return null;
        }
        return this.board.get(index).getName();
    }

    public Location boardElement(int index){
        return this.board.get(index);
    }

    public BoardModel getModel(){
        return (BoardModel) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}
