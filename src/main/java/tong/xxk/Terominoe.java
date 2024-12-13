package tong.xxk;

import java.util.Random;

public class Terominoe {
    // 定义常量来表示不同的形状
    public static final int NO_SHAPE = 0;
    public static final int Z_SHAPE = 1;
    public static final int S_SHAPE = 2;
    public static final int LINE_SHAPE = 3;
    public static final int T_SHAPE = 4;
    public static final int SQUARE_SHAPE = 5;
    public static final int L_SHAPE = 6;
    public static final int MIRRORED_L_SHAPE = 7;
    private static final int[][][] SHAPES = {
            {{1, 1, 1, 1}},
            {{1, 1}, {1, 1}},
            {{0, 1, 0}, {1, 1, 1}},
            {{1, 1, 0}, {0, 1, 1}},
            {{0, 1, 1}, {1, 1, 0}},
            {{1, 1, 1}, {0, 1, 0}},
            {{1, 1, 1}, {1, 0, 0}}
    };

    private int[][] shape;
    private int x, y;

    public Terominoe() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7;
        setShape(SHAPES[x]);
        this.x = 0;
        this.y = 0;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public Terominoe rotateRight() {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        Terominoe newPiece = new Terominoe();
        newPiece.setShape(rotated);
        return newPiece;
    }

    public Terominoe rotateLeft() {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotated[shape[i].length - 1 - j][i] = shape[i][j];
            }
        }
        Terominoe newPiece = new Terominoe();
        newPiece.setShape(rotated);
        return newPiece;
    }
}
