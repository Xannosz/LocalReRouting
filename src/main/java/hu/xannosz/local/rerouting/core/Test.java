package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.util.LatinSquare;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        System.out.println(""+new Date());
         Util.createLatinSquares(300);
        System.out.println(""+new Date());
    }
}
