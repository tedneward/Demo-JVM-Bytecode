package com.newardassociates.demo;

// Keep in mind that most IDEs and tools will flag the use
// of the MessageProvider below as an error, since they won't
// know how to resolve the name (since they don't know about 
// jasm).
public class App {
    public static void main(String[] args) {
        System.out.println(new MessageProvider().getMessage());
    }
}
