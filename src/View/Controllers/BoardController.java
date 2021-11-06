package View.Controllers;

import Model.BoardModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardController implements ActionListener {

    private final BoardModel model;

    public BoardController(BoardModel model){
        this.model = model;
    }


    /**
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] inputs = e.getActionCommand().split(" ");
        this.model.playCurrPlayerTurn(Integer.parseInt(inputs[0]));
    }
}
