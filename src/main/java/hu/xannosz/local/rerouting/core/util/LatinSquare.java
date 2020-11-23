package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.microtools.dimensions.Matrix;
import lombok.Getter;

import java.util.Random;

public class LatinSquare extends Matrix<Integer> {

    @Getter
    private final int n;

    public LatinSquare(int n) {
        super(n, n);
        this.n = n;
        for (int i = 0; i < n; i++) {
            addDiagonal(0, i, i);
            addDiagonal(n - i, 0, i);
        }
    }

    public LatinSquare(LatinSquare latinSquare) {
        super(latinSquare.getN(), latinSquare.getN());
        this.n = latinSquare.getN();
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                addItem(k, j, latinSquare.getItem(k, j));
            }
        }
    }

    public void addDiagonal(int x, int y, int item) {
        for (int i = 0; i < n; i++) {
            if (x + i < n && y + i < n) {
                addItem(x + i, y + i, item);
            }
        }
    }

    public void permuteRows() {
        swapRows(new Random().nextInt(n), new Random().nextInt(n));
    }

    public void permuteColumns() {
        swapColumns(new Random().nextInt(n), new Random().nextInt(n));
    }

    public void permuteSymbols() {
        swapSymbols(new Random().nextInt(n), new Random().nextInt(n));
    }

    public void swapRows(int i, int e) {
        for (int k = 0; k < n; k++) {
            int item = getItem(i, k);
            addItem(i, k, getItem(e, k));
            addItem(e, k, item);
        }
    }

    public void swapColumns(int i, int e) {
        for (int k = 0; k < n; k++) {
            int item = getItem(k, i);
            addItem(k, i, getItem(k, e));
            addItem(k, e, item);
        }
    }

    public void swapSymbols(int i, int e) {
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                if (getItem(k, j) == i) {
                    addItem(k, j, e);
                } else if (getItem(k, j) == e) {
                    addItem(k, j, i);
                }
            }
        }
    }
}
