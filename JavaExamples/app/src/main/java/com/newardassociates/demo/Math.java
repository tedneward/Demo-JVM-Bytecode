/*
 * 
 */
package com.newardassociates.demo;

public class Math {
    // {{## BEGIN add-with-parameters ##}}
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    // {{## END add-with-parameters ##}}
    // {{## BEGIN add-with-locals ##}}
    public static int add() {
        int lhs = 5;
        int rhs = 28;
        return lhs + rhs;
    }
    // {{## END add-with-locals ##}}
    // {{## BEGIN sum-array ##}}
    public static int sumArray() {
        int[] numbers = {12, 21, 37};
        int total = 0;
        for (int i : numbers) {
            total += i;
        }
        return total;
    }
    // {{## END sum-array ##}}

    // These are here to help pick out where
    // array-initialization leaves off and
    // iteration begins.
    public static int sumArray_newarray() {
        int[] numbers = {12, 21, 37};
        return -1;
    }
    public static int sumArray_iterate(int[] numbers) {
        for (int i : numbers) {
        }
        return -1;
    }
}
