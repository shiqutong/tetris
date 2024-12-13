package tong.xxk;

import java.util.Arrays;
import java.util.Random;

/**
 * Tetrominoe枚举类定义了俄罗斯方块游戏中的方块状态
 * 包括没有形状、填充、闪烁和溶解状态
 */
public enum Tetrominoe {
    // 表示没有形状的状态
    NoShape(new int[][]{{0}}),
    IShape(new int[][] {
            {1, 1, 1, 1}
    }),
    OShape(new int[][] {
            {1, 1},
            {1, 1}
    }),
    TShape(new int[][] {
            {0, 1, 0},
            {1, 1, 1}
    }),
    SShape(new int[][] {
            {0, 1, 1},
            {1, 1, 0}
    }),
    ZShape(new int[][] {
            {1, 1, 0},
            {0, 1, 1}
    }),
    JShape(new int[][] {
            {1, 0, 0},
            {1, 1, 1}
    }),
    LShape(new int[][] {
            {0, 0, 1},
            {1, 1, 1}
    }),

    // 表示方块填充的状态
    Filled(new int[0][0]),
    // 表示方块闪烁的状态
    Flash(new int[0][0]),
    // 表示方块溶解的状态
    Dissolving(new int[0][0]);

    // 方块的透明度，默认为完全不透明
    private float alpha = 1.0f;

    Tetrominoe(int[][] shape) {
        this.shape = shape;
    }

    /**
     * 获取当前方块的透明度
     * @return 当前方块的透明度值
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 设置方块的透明度
     * @param alpha 新的透明度值，范围在0.0到1.0之间
     */
    public void setAlpha(float alpha) {
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException("Alpha value must be between 0.0 and 1.0");
        }
        this.alpha = alpha;
    }

    private int[][] shape;
    private int x, y;

    public int[][] getShape() {
        return shape;
    }

    public void setShape(Tetrominoe tetrominoe) {
        this.alpha = tetrominoe.alpha;
        this.shape = tetrominoe.shape;
    }

    public Tetrominoe rotateRight() {
        return rotate(1);
    }

    public Tetrominoe rotateLeft() {
        return rotate(-1);
    }
    public int minX() {
        int minX = Integer.MAX_VALUE;
        for (int x = 0; x < shape[0].length; x++) {
            for (int y = 0; y < shape.length; y++) {
                if (shape[y][x] != 0) {
                    minX = Math.min(minX, x);
                }
            }
        }
        return minX;
    }

    public int minY() {
        int minY = Integer.MAX_VALUE;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] != 0) {
                    minY = Math.min(minY, y);
                }
            }
        }
        return minY;
    }
    public int setX(int x) {
        this.x = x;
        return x;
    }

    public int getX() {
        return x;
    }

    public int setY(int y) {
        this.y = y;
        return y;
    }

    public int getY() {
        return y;
    }
    private Tetrominoe rotate(int direction) {
        if (this == NoShape || this == OShape) {
            return this; // NoShape and OShape do not change on rotation
        }

        int[][] rotatedShape = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (direction == 1) {
                    rotatedShape[j][shape.length - 1 - i] = shape[i][j];
                } else {
                    rotatedShape[shape[i].length - 1 - j][i] = shape[i][j];
                }
            }
        }
        return getTetrominoeByShape(rotatedShape);
    }

    private Tetrominoe getTetrominoeByShape(int[][] shape) {
        for (Tetrominoe t : Tetrominoe.values()) {
            if (arraysDeepEquals(t.getShape(), shape)) {
                return t;
            }
        }
        return NoShape; // Default to NoShape if no match found
    }

    private boolean arraysDeepEquals(int[][] a1, int[][] a2) {
        if (a1.length != a2.length) return false;
        for (int i = 0; i < a1.length; i++) {
            if (!Arrays.equals(a1[i], a2[i])) return false;
        }
        return true;
    }

    public static Tetrominoe getRandomTetrominoe() {
        Tetrominoe[] values = Tetrominoe.values();
        int randomIndex = new Random().nextInt(values.length - 1) + 1; // Exclude NoShape
        return values[randomIndex];
    }
    public void setRandomShape() {
        Tetrominoe randomTetrominoe = getRandomTetrominoe();
        this.shape = randomTetrominoe.getShape();
    }

}
