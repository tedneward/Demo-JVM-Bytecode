package com.newardassociates.demo;

public class Assertions {
    public static void main(String... args) {
        assert(args != null);
        assert(args[0] instanceof String);
    }
}
