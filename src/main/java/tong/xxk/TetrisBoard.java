package tong.xxk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import tong.xxk.config.Config;

public class TetrisBoard extends JPanel implements ActionListener {
    private Timer timer;
    private Terominoe currentPiece;
    private int[][] board;

    public TetrisBoard() {
        initBoard();
        initGame();
    }

    /**
     * 初始化游戏面板的方法
     * 设置面板为可聚焦，添加键盘事件监听器，设置背景色和双缓冲，并启动定时器
     * 该方法在游戏启动时被调用，用于初始化游戏界面和游戏循环
     */
    private void initBoard() {
        // 设置面板为可聚焦，以便接收键盘事件
        setFocusable(true);
        // 添加键盘事件监听器，用于处理键盘事件
        addKeyListener(new TAdapter());
        // 设置背景色为黑色，以便在游戏面板上清晰地显示游戏元素
        setBackground(Color.BLACK);
        // 设置双缓冲，以减少图形绘制时的闪烁现象
        setDoubleBuffered(true);
        // 创建并启动定时器，用于控制游戏循环
        timer = new Timer(400, this);
        timer.start();
    }
    /**
     * 初始化游戏状态
     * 此方法在游戏开始时调用，用于设置游戏的初始状态
     */
    private void initGame() {
        board = new int[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
        createNewPiece();
    }
    /**
     * 创建一个新的四联立方块
     *
     * 此方法用于在游戏板上生成一个新的四联立方块，并尝试将其放置在游戏板的中心顶部
     * 如果无法放置，表明游戏板上已经没有足够的空间，游戏结束
     */
    private void createNewPiece() {
        currentPiece = new Terominoe();
        if (!tryMove(currentPiece, Config.BOARD_WIDTH / 2, 0)) { // 修改为 0
            timer.stop();
            // 显示游戏结束对话框
            JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            // 清空游戏板
            board = new int[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
            // 重新初始化游戏
            initGame();
        }
    }

    /**
     * 重绘组件中的方法，用于自定义组件的绘制
     * @param g Graphics对象，用于绘制
     */
    @Override
    protected void paintComponent(Graphics g) {
        // 调用父类的paintComponent方法，确保基本绘制工作已经完成
        super.paintComponent(g);
        // 绘制游戏棋盘
        drawBoard(g);
        // 如果当前棋子不为空，则绘制当前棋子
        if (currentPiece != null) {
            drawPiece(g, currentPiece);
        }
    }
    /**
     * 绘制游戏面板的方法
     * 遍历二维数组board，绘制每个非空方块为红色
     * @param g Graphics对象，用于绘图
     */
    private void drawBoard(Graphics g) {
        for (int i = 0; i < Config.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Config.BOARD_WIDTH; j++) {
                if (board[i][j] != 0) {
                    drawSquare(g, j * Config.BLOCK_SIZE, i * Config.BLOCK_SIZE, Color.RED);
                }
            }
        }
        // 如果当前方块不为空，则绘制当前方块
        if (currentPiece != null) {
            drawPiece(g, currentPiece);
        }
    }
    /**
     * 绘制当前方块的方法
     * 根据方块的形状和位置，绘制每个非空部分为蓝色
     * @param g Graphics对象，用于绘图
     * @param piece 当前方块对象
     */
    private void drawPiece(Graphics g, Terominoe piece) {
        int x = piece.getX();
        int y = piece.getY();
        int[][] shape = piece.getShape();

        // 获取 shape 数组的实际大小
        int rows = shape.length;
        int cols = shape[0].length;
        // 打印方块的位置和形状，用于调试
        System.out.println("Drawing piece at (" + x + ", " + y + ") with shape:");
        for (int[] row : shape) {
            System.out.println(Arrays.toString(row));
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (shape[i][j] != 0) {
                    drawSquare(g, (x + j) * Config.BLOCK_SIZE, (y + i) * Config.BLOCK_SIZE, Color.BLUE);
                }
            }
        }
    }

    /**
     * 绘制单个方块的方法
     * 根据指定的位置和颜色，绘制一个方块，并添加边框效果
     * @param g Graphics对象，用于绘图
     * @param x 方块的x坐标
     * @param y 方块的y坐标
     * @param color 方块的颜色
     */
    private void drawSquare(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x + 1, y + 1, Config.BLOCK_SIZE - 2, Config.BLOCK_SIZE - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + Config.BLOCK_SIZE - 1, x, y);
        g.drawLine(x, y, x + Config.BLOCK_SIZE - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + Config.BLOCK_SIZE - 1, x + Config.BLOCK_SIZE - 1, y + Config.BLOCK_SIZE - 1);
        g.drawLine(x + Config.BLOCK_SIZE - 1, y + Config.BLOCK_SIZE - 1, x + Config.BLOCK_SIZE - 1, y + 1);
    }
    /**
     * 尝试移动方块的方法
     * 检查方块在新位置是否合法，如果合法则更新方块的位置并重新绘制游戏界面
     * @param newPiece 新的方块对象
     * @param newX 方块的新x坐标
     * @param newY 方块的新y坐标
     * @return 如果移动成功则返回true，否则返回false
     */
    private boolean tryMove(Terominoe newPiece, int newX, int newY) {
        int[][] shape = newPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int x = newX + j;
                    int y = newY + i; // 修改为 +i
                    if (x < 0 || x >= Config.BOARD_WIDTH || y < 0 || y >= Config.BOARD_HEIGHT) {
                        return false;
                    }
                    if (board[y][x] != 0) {
                        return false;
                    }
                }
            }
        }
        currentPiece = newPiece;
        currentPiece.setX(newX);
        currentPiece.setY(newY);
        repaint();
        return true;
    }
    /**
     * 处理方块下落一行的动作
     * 如果方块无法下落，则调用pieceDropped方法
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        oneLineDown();
        System.out.println("Timer tick"); // 添加调试信息
    }
    /**
     * 方块下落一行的方法
     * 如果方块无法下落，则调用pieceDropped方法
     */
    private void oneLineDown() {
        if (!tryMove(currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) { // 修改为 +1
            pieceDropped();
        }
    }
    /**
     * 处理方块掉落到底部的方法
     * 更新游戏面板，移除满行，并创建新的方块
     */
    private void pieceDropped() {
        int[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) { // 使用 shape.length 而不是固定的 4
            for (int j = 0; j < shape[i].length; j++) { // 使用 shape[i].length 而不是固定的 4
                if (shape[i][j] != 0) {
                    int y = currentPiece.getY() + i;
                    int x = currentPiece.getX() + j;
                    if (y >= 0 && y < Config.BOARD_HEIGHT && x >= 0 && x < Config.BOARD_WIDTH) {
                        board[y][x] = 1;
                    } else {
                        // 可以在这里添加日志或处理异常情况
                        System.out.println("Index out of bounds: y=" + y + ", x=" + x);
                    }
                }
            }
        }
        removeFullLines();
        createNewPiece();
    }
    /**
     * 移除满行的方法
     * 检查游戏面板是否有满行，如果有则移除，并将上方的行向下移动
     */
    private void removeFullLines() {
        int numFullLines = 0;
        for (int i = Config.BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < Config.BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                numFullLines++;
                for (int k = i; k < Config.BOARD_HEIGHT - 1; k++) {
                    System.arraycopy(board[k + 1], 0, board[k], 0, Config.BOARD_WIDTH);
                }
            }
        }
        if (numFullLines > 0) {
            repaint();
        }
    }
    /**
     * 处理键盘事件的方法
     * 根据用户按下的键，执行相应的动作
     */
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    tryMove(currentPiece, currentPiece.getX() - 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(currentPiece, currentPiece.getX() + 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_DOWN:
                    tryMove(currentPiece.rotateRight(), currentPiece.getX(), currentPiece.getY());
                    break;
                case KeyEvent.VK_UP:
                    tryMove(currentPiece.rotateLeft(), currentPiece.getX(), currentPiece.getY());
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
            }
        }
    }
    /**
     * 方块快速下落到底部的方法
     * 将方块快速移动到到底部的位置，并调用pieceDropped方法
     */
    private void dropDown() {
        int newY = currentPiece.getY();
        while (newY < Config.BOARD_HEIGHT && tryMove(currentPiece, currentPiece.getX(), newY)) { // 修改为 < Config.BOARD_HEIGHT
            newY++;
        }
        pieceDropped();
    }
}
