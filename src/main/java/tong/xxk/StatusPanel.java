package tong.xxk;

import tong.xxk.config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel {
    private Timer timer;
    private final JLabel scoreLabel;
    private final JLabel levelLabel;
    private final JLabel gamestateLabel;
    private TetrisBoard tetrisBoard; // 声明 TetrisBoard 实例


    public StatusPanel(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard; // 确保在构造函数中初始化 tetrisBoard
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Status"));

        // 设置面板的首选大小
        setPreferredSize(new Dimension(Config.StatusPANEL_WIDTH, Config.StatusPANEL_HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gamestateLabel = new JLabel("Game State: " + tetrisBoard.getState());
        add(gamestateLabel, gbc);

        gbc.gridy = 1;
        scoreLabel = new JLabel("Game Score: " + tetrisBoard.getScore());
        add(scoreLabel, gbc);

        gbc.gridy = 2;
        levelLabel = new JLabel("Game Level: " + tetrisBoard.getLevel());
        add(levelLabel, gbc);

        startTimer();
    }
    // 设置定时器，每秒更新一次状态
    public void startTimer() {
        timer = new Timer(1000, e -> updateStatus());
        timer.start();
       }
    public void updateStatus() {
        gamestateLabel.setText("Game State: " +tetrisBoard.getState());
        scoreLabel.setText("Game Score: "+tetrisBoard.getScore());
        levelLabel.setText("Game Level: " +tetrisBoard.getLevel());
    }
}
