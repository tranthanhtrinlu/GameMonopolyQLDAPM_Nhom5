package Testing;

import Model.BoardModel;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the BoardModel class.
 */
public class BoardModelTest {

    /**
     * Test to ensure that money is properly added to the center of the board.
     */
    @Test
    public void addToCenterMoney() {
        BoardModel.addToCenterMoney(100);
        assertEquals(100, BoardModel.getCenterMoney());
    }

    /**
     * Test for the status update.
     */
    @Test
    public void testStatusUpdate() {
        BoardModel bm = new BoardModel();
        bm.setNumberOfPlayers(1);
        bm.updateStatus();
        assertEquals(bm.getStatus(), BoardModel.Status.FINISHED);
    }


    /**
     * test for loading US version
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void testLoadUSVersion() throws IOException, ParserConfigurationException, SAXException {
        BoardModel bm = new BoardModel();
        bm.initializeBoard("src/LoadXML/NewBoardModel.xml");
        assertEquals(40,bm.getSizeOfBoard());
    }


    /**
     * test for loading UK version
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void testLoadUKVersion() throws IOException, ParserConfigurationException, SAXException {
        BoardModel bm = new BoardModel();
        bm.initializeBoard("src/LoadXML/UKBoardModel.xml");
        assertEquals(40,bm.getSizeOfBoard());
    }

    /**
     * test for loading a saved file
     * loading save file is generalized for any file for any version
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void testLoadSavedFile() throws ParserConfigurationException, IOException, SAXException {
        BoardModel bm = new BoardModel();
        bm.loadSavedXML("src/SaveXML/testSavedFile.xml");
        assertEquals(40,bm.getSizeOfBoard());
        assertEquals(5, bm.getNumOfPlayers());
        assertEquals(3,bm.getCurrentTurn());
        assertEquals(2,bm.getRoll1());
        assertEquals(5,bm.getRoll2());
        assertEquals(0, BoardModel.getCenterMoney());
        assertEquals(BoardModel.TypeOfBoards.US.getVersion(), bm.getGameVersion());
    }




}