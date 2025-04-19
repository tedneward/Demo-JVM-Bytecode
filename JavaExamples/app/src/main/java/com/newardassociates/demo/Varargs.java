/*
 *
 */
package com.newardassociates.demo;

public class Varargs {
    public static void main(String[] args) {
        for (String a : args) {
            System.out.println("Received arg: " + a);
        }
    }
}
