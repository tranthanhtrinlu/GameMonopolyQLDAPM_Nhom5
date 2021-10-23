import java.util.EventObject;
import java.util.List;

public class BoardEvent extends EventObject {

    private List<Location> board;
    private boolean doubles;
    private int roll;
    public BoardEvent(BoardModel boardModel, List<Location> board, boolean doubles, int roll) {
        super(boardModel);
        this.board = board;
        this.doubles = doubles;
        this.roll = roll;
    }

    public int getRoll() {
        return this.roll;
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
