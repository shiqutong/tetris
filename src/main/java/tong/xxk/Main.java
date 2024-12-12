package tong.xxk;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TetrisBoard tetrisBoard = new TetrisBoard();
        frame.add(tetrisBoard);

        frame.pack(); // 自动调整窗口大小以适应其内容
        frame.setLocationRelativeTo(null); // 居中显示窗口
        frame.setVisible(true);
    }
}