package tong.xxk;

import tong.xxk.config.Config;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Config.FrameWIDTH, Config.FrameHEIGHT); // 设置窗口大小
        frame.setLocationRelativeTo(null); // 居中显示窗口
        // 设置 JFrame 的布局管理器为 GridBagLayout
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        // 创建 GridBagConstraints 实例
        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.BOTH; // 让组件填充可用空间
        // 创建 TetrisBoard 实例
        TetrisBoard tetrisBoard = new TetrisBoard();
        // 创建并添加 StatusPanel
        StatusPanel statusPanel = new StatusPanel(tetrisBoard);
        // 创建并添加 controlPanel
        ControlPanel controlPanel = new ControlPanel(tetrisBoard);
        // 创建并添加 tetrisBoard
        gbc.gridx = 0;  // 设置组件起始位置的列索引
        gbc.gridy = 0;  // 设置组件起始位置的行索引
        gbc.gridwidth = 1;  // 设置组件占据的列数
        gbc.gridheight = 1;  // 设置组件占据的行数
        gbc.weightx = 0.0;  // 设置组件在水平方向上扩展的权重
        gbc.weighty = 0.0;  // 设置组件在垂直方向上扩展的权重
        // 右上角对齐
        gbc.anchor = GridBagConstraints.NORTHWEST;
        frame.add(tetrisBoard, gbc);

        // 创建并添加 statusPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0; // 不可扩展
        gbc.weighty = 0.0; // 不可扩展
        // 左上角对齐
        gbc.anchor = GridBagConstraints.NORTHWEST;
        frame.add(statusPanel, gbc);

        // 创建并添加 controlPanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 跨越两列
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0; // 可以根据需要调整高度比例
//        gbc.anchor = GridBagConstraints.SOUTHWEST;
        frame.add(controlPanel, gbc);

        frame.setResizable(false);  // 禁用窗口调整大小
        frame.setVisible(true);
    }
}