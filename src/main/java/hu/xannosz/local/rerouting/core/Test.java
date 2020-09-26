package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            list.add(i);
        }

        List<List<Integer>> result = Util.getPermutations(list,300000000);
/*        for (List<Integer> row : result) {
            for (Integer element : row) {
                System.out.print("" + element);
                System.out.print(" ");
            }
            System.out.print("\n");
        }*/
    }
}
