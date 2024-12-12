package tong.xxk;

import java.util.Random;

public class Terominoe {
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
