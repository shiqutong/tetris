package tong.xxk;

import javax.swing.*;
import java.awt.*;

public class TetrisMainPanel extends JPanel {
    private TetrisBoard tetrisBoard;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;

    public TetrisMainPanel() {
        setLayout(new BorderLayout());

        tetrisBoard = new TetrisBoard();
        controlPanel = new ControlPanel(tetrisBoard);
        statusPanel = new StatusPanel();

        add(tetrisBoard, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(statusPanel, BorderLayout.EAST);
    }
}

