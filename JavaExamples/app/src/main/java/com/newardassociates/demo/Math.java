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
}
