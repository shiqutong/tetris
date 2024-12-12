package tong.xxk;

import javax.swing.*;
import tong.xxk.config.Config;

public class Tetris extends JFrame {
    public Tetris() {
        setTitle("俄罗斯方块");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Config.BOARD_WIDTH * Config.BLOCK_SIZE, Config.BOARD_HEIGHT * Config.BLOCK_SIZE);
        setLocationRelativeTo(null);

        TetrisBoard board = new TetrisBoard();
        add(board);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}