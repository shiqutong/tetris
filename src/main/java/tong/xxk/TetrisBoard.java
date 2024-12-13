package tong.xxk;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private GameState state;

    public TetrisBoard() {
        initBoard();
    }

    // 获取当前游戏状态
    public GameState getState() {
        return state;
    }
    // 设置新的游戏状态
    public void setState(GameState newState) {
        this.state = newState;
        this.state.enter(this);
    }
    /**
     * 初始化游戏面板的方法
     * 设置面板为可聚焦，添加键盘事件监听器，设置背景色和双缓冲，并启动定时器
     * 该方法在游戏启动时被调用，用于初始化游戏界面和游戏循环
     */
    private void initBoard() {
        setLayout(new BorderLayout()); // 设置布局管理器
        setPreferredSize(new Dimension(Config.GamePanel_WIDTH, Config.GamePanel_HEIGHT));
        // 设置面板为可聚焦，以便接收键盘事件
        setFocusable(true);
        // 添加键盘事件监听器，用于处理键盘事件
        addKeyListener(new TAdapter());
        // 设置双缓冲，以减少图形绘制时的闪烁现象
        setDoubleBuffered(true);
        setBorder(new LineBorder(Color.yellow, 1));
        setState(GameState.START);
    }

    /**
     * 初始化游戏状态
     * 此方法在游戏开始时调用，用于设置游戏的初始状态
     */
   public void initGame() {
        board = new int[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
        createNewPiece();
      // 创建并启动定时器，用于控制游戏循环
        timer = new Timer(400, e -> {
            if (getState()!=GameState.GAME_OVER) {
                oneLineDown();
            } else {
                timer.stop();
            }
        });
        timer.start();
        setState(GameState.RUNNING);
    }
    /**
     * 创建一个新的四联立方块
     * 此方法用于在游戏板上生成一个新的四联立方块，并尝试将其放置在游戏板的中心顶部
     * 如果无法放置，表明游戏板上已经没有足够的空间，游戏结束
     */
    private void createNewPiece() {
        currentPiece = new Terominoe();
        if (!tryMove(currentPiece, Config.BOARD_WIDTH / 2, 0)) { // 修改为 0
            timer.stop();
            setState(GameState.GAME_OVER);
            repaint();
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
        // 绘制渐变背景
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(50, 50, 50), getWidth(), getHeight(), new Color(100, 100, 100));
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        // 绘制游戏棋盘
        doDrawing(g);
        // 如果当前棋子不为空，则绘制当前棋子
        if (currentPiece != null) {
            drawPiece(g, currentPiece);
        }
    }
    private void doDrawing(Graphics g) {
        if (getState() == GameState.RUNNING) {
            drawBoard(g);
        } else if (getState() == GameState.GAME_OVER||getState()==GameState.START){
            drawGameOver(g);
        }
    }
    private void drawGameOver(Graphics g) {
        // 绘制 "GAME OVER" 图案
        String gameOverText = "俄罗斯方块";
        String gameOverText2 = "TETRIS";
        Font font = new Font("SimSun", Font.BOLD, 18);
        g.setFont(font);
        g.setColor(Color.GREEN);
        FontMetrics fm = g.getFontMetrics();

        // 计算第一行文字的宽度和高度
        int textWidth = fm.stringWidth(gameOverText);
        int textHeight = fm.getHeight();

        // 计算第一行文字的 y 坐标
        int y1 = (Config.BOARD_HEIGHT * Config.BLOCK_SIZE + textHeight) / 2;

        // 绘制第一行文字
        g.drawString(gameOverText, (Config.BOARD_WIDTH * Config.BLOCK_SIZE - textWidth) / 2, y1-20);

        // 计算第二行文字的宽度和高度
        int textWidth2 = fm.stringWidth(gameOverText2);
        int textHeight2 = fm.getHeight();

        // 计算第二行文字的 y 坐标，加上第一行文字的高度
        int y2 = y1 + textHeight;

        // 绘制第二行文字
        g.drawString(gameOverText2, (Config.BOARD_WIDTH * Config.BLOCK_SIZE - textWidth2) / 2, y2-20);
    }
    /**
     * 绘制游戏面板的方法
     * 遍历二维数组board，绘制每个非空方块为红色
     * @param g Graphics对象，用于绘图
     */
    private void drawBoard(Graphics g) {
        if (!(getState() == GameState.RUNNING)) {
            return; // 如果游戏结束，则不执行定时器动作
        }
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
        if (getState() == GameState.GAME_OVER) {
            return; // 如果游戏结束，则不执行定时器动作
        }
        int x = piece.getX();
        int y = piece.getY();
        int[][] shape = piece.getShape();

        // 获取 shape 数组的实际大小
        int rows = shape.length;
        int cols = shape[0].length;
        // 打印方块的位置和形状，用于调试
//        System.out.println("Drawing piece at (" + x + ", " + y + ") with shape:");
//        for (int[] row : shape) {
//            System.out.println(Arrays.toString(row));
//        }

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
    private boolean tryMove(Terominoe newPiece, int newX, int newY, boolean isRotation) {
        int[][] shape = newPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int x = newX + j;
                    int y = newY + i;
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

    // 重载 tryMove 方法，不带 isRotation 参数，默认为 false
    private boolean tryMove(Terominoe newPiece, int newX, int newY) {
        return tryMove(newPiece, newX, newY, false);
    }

    /**
     * 处理方块下落一行的动作
     * 如果方块无法下落，则调用pieceDropped方法
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (getState() == GameState.GAME_OVER) {
            return; // 如果游戏结束，则不执行定时器动作
        }
        oneLineDown();
//        System.out.println("Timer tick"); // 添加调试信息
    }
    /**
     * 方块下落一行的方法
     * 如果方块无法下落，则调用pieceDropped方法
     */
    private void oneLineDown() {
        if (getState() == GameState.GAME_OVER) {
            return; // 如果游戏结束，则不执行一行下降的逻辑
        }
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
        if (getState() == GameState.RUNNING) {
            createNewPiece();
        }
    }
    /**
     * 移除满行的方法
     * 检查游戏面板是否有满行，如果有则移除，并将上方的行向下移动
     */
    private void removeFullLines() {
        int numFullLines = 0;
        int[] fullLines = new int[Config.BOARD_HEIGHT];
        int fullLineCount = 0;

        // 记录所有满行的索引
        for (int i = Config.BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < Config.BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                fullLines[fullLineCount++] = i;
                numFullLines++;
            }
        }

        // 从下往上移动上方的方块
        if (numFullLines > 0) {
            int shiftDownFrom = Config.BOARD_HEIGHT - 1;
            for (int i = Config.BOARD_HEIGHT - 1; i >= 0; i--) {
                boolean isFullLine = false;
                for (int j = 0; j < fullLineCount; j++) {
                    if (i == fullLines[j]) {
                        isFullLine = true;
                        break;
                    }
                }
                if (!isFullLine) {
                    board[shiftDownFrom] = board[i];
                    shiftDownFrom--;
                }
            }

            // 清除剩余的行
            for (int i = shiftDownFrom; i >= 0; i--) {
                Arrays.fill(board[i], 0);
            }

            repaint();
        }
    }
    //暂停游戏
    public void pause() {
        timer.stop();
       setState(GameState.PAUSED);
    }
    //继续游戏
    public void resume() {
        timer.start();
       setState(GameState.RUNNING);
    }

    /**
     * 获取当前的得分
     *
     * 此方法用于返回当前游戏或应用中的得分情况
     * 返回值为null表示尚未实现具体的得分逻辑或数据
     *
     * @return 当前得分的字符串表示，如果未实现则返回null
     */
    public String getScore() {
        return null;
    }

    /**
     * 获取当前的等级
     *
     * 此方法用于返回当前游戏或应用中的等级情况
     * 返回值为null表示尚未实现具体的等级逻辑或数据
     *
     * @return 当前等级的字符串表示，如果未实现则返回null
     */
    public String getLevel() {
        return null;
    }



    /**
     * 处理键盘事件的方法
     * 根据用户按下的键，执行相应的动作
     */
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!(getState() == GameState.RUNNING)) {
                return;
            }
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    tryMove(currentPiece, currentPiece.getX() - 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(currentPiece, currentPiece.getX() + 1, currentPiece.getY());
                    break;
                case KeyEvent.VK_DOWN:
                    tryRotateRight();
                    break;
                case KeyEvent.VK_UP:
                    tryRotateLeft();
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
            }
        }
    }
    private void tryRotateRight() {
        Terominoe rotatedPiece = currentPiece.rotateRight();
        int newX = currentPiece.getX();
        int newY = currentPiece.getY();

        // Check if the rotated piece can fit in the current position
        if (tryMove(rotatedPiece, newX, newY, true)) {
            return;
        }

        // Try moving left or right to fit the rotated piece
        if (newX > 0 && tryMove(rotatedPiece, newX - 1, newY, true)) {
            return;
        }
        if (newX < Config.BOARD_WIDTH - 1 && tryMove(rotatedPiece, newX + 1, newY, true)) {
            return;
        }

        // Try moving down to fit the rotated piece
        if (newY < Config.BOARD_HEIGHT - 1 && tryMove(rotatedPiece, newX, newY + 1, true)) {
            return;
        }
    }

    private void tryRotateLeft() {
        Terominoe rotatedPiece = currentPiece.rotateLeft();
        int newX = currentPiece.getX();
        int newY = currentPiece.getY();

        // Check if the rotated piece can fit in the current position
        if (tryMove(rotatedPiece, newX, newY, true)) {
            return;
        }

        // Try moving left or right to fit the rotated piece
        if (newX > 0 && tryMove(rotatedPiece, newX - 1, newY, true)) {
            return;
        }
        if (newX < Config.BOARD_WIDTH - 1 && tryMove(rotatedPiece, newX + 1, newY, true)) {
            return;
        }

        // Try moving down to fit the rotated piece
        if (newY < Config.BOARD_HEIGHT - 1 && tryMove(rotatedPiece, newX, newY + 1, true)) {
            return;
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
    public void requestGameFocus() {
        requestFocusInWindow();
    }

}
