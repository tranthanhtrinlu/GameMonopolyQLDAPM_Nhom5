package View.Controllers;
import Model.BoardModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * BoardController that handles the input of the current player choice.
 * @author Max Curkovic
 */
public class BoardController implements ActionListener {

    private final BoardModel model;

    /**
     * constructor for boardController
     * @param model BoardModel
     */
    public BoardController(BoardModel model){
        this.model = model;
    }

    /**
     * The Action Event for when a certain button is pressed according to the command of the button
     * @param e ActionEvent, the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] inputs = e.getActionCommand().split(" ");
        this.model.playCurrPlayerTurn(Integer.parseInt(inputs[0]));
    }
}
