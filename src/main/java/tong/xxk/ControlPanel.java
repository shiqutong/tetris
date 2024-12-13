package tong.xxk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton startButton;
    private JButton pauseButton;
    private JButton exitButton;
    private TetrisBoard tetrisBoard; // 声明 TetrisBoard 实例

    public ControlPanel(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard; // 确保在构造函数中初始化 tetrisBoard
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createTitledBorder("Controls"));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        exitButton = new JButton("Exit");

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
                //通过游戏装态判断
                if (tetrisBoard.getState()== GameState.RUNNING) {
                    // 暂停游戏逻辑
                    tetrisBoard.pause();
                }else
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

        add(startButton);
        add(pauseButton);
        add(exitButton);
    }
}
