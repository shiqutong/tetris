package tong.xxk;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel scoreLabel;
    private final JLabel levelLabel;

    public StatusPanel() {
        setLayout(new GridLayout(2, 1));
        setBorder(BorderFactory.createTitledBorder("Status"));

        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");

        add(scoreLabel);
        add(levelLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }
}
