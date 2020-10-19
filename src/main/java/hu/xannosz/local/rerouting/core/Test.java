package hu.xannosz.local.rerouting.core;

import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        IntStream.range(0, 8)
                .forEach(System.out::println);
/*        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            list.add(i);
        }

        List<List<Integer>> result = Util.getPermutations(list,300000000);*/
/*        for (List<Integer> row : result) {
            for (Integer element : row) {
                System.out.print("" + element);
                System.out.print(" ");
            }
            System.out.print("\n");
        }*/
    }
}
