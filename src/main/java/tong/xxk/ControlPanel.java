package tong.xxk;

import tong.xxk.config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton startButton;
    private JButton pauseButton;
    private JButton exitButton;
    private TetrisBoard tetrisBoard; // 声明 TetrisBoard 实例
    // 定义常量
    private static final int BUTTON_COLUMN = 0;
    private static final int START_BUTTON_ROW = 0;
    private static final int PAUSE_BUTTON_ROW = 1;
    private static final int EXIT_BUTTON_ROW = 2;

    public ControlPanel(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard; // 确保在构造函数中初始化 tetrisBoard
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createTitledBorder("Controls"));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        exitButton = new JButton("Exit");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距

        // 设置面板的首选大小
        setPreferredSize(new Dimension(Config.ControlPanel_WIDTH, Config.ControlPanel_HEIGHT));
        setBackground(Color.pink);
        // 设置按钮样式
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(new Color(0x4CAF50));
        pauseButton.setBackground(new Color(0x2196F3));
        exitButton.setBackground(new Color(0xF44336));
        startButton.setForeground(Color.WHITE);
        pauseButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        pauseButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);
        startButton.setOpaque(true);
        pauseButton.setOpaque(true);
        exitButton.setOpaque(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 开始游戏逻辑
                // 为TetrisBoard重新请求焦点
                tetrisBoard.requestGameFocus();
                tetrisBoard.initGame();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetrisBoard.requestGameFocus();
                if(tetrisBoard.getState() == GameState.START){
                    return;
                }
                //通过游戏装态判断
                if (tetrisBoard.getState() == GameState.RUNNING) {
                    // 暂停游戏逻辑
                    tetrisBoard.pause();
                } else
                    // 恢复游戏逻辑
                    tetrisBoard.resume();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


// 添加按钮到面板
        try {
            gbc.gridx = BUTTON_COLUMN;
            gbc.gridy = START_BUTTON_ROW;
            add(startButton, gbc);

            gbc.gridy = PAUSE_BUTTON_ROW;
            add(pauseButton, gbc);

            gbc.gridy = EXIT_BUTTON_ROW;
            add(exitButton, gbc);
        } catch (Exception e) {
            // 处理异常
            System.err.println("添加按钮时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
