package tong.xxk;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600); // 设置窗口大小
        frame.setLocationRelativeTo(null); // 居中显示窗口
        TetrisMainPanel mainPanel = new TetrisMainPanel();
        frame.add(mainPanel);
        frame.pack(); // 自动调整窗口大小以适应其内容
        // 禁用窗口调整大小
        frame.setResizable(false);
        frame.setVisible(true);
    }
}