/*
 * 
 */
package com.newardassociates.demo;

public class StringConcat {
    public static String concatThis() {
        int x = 5;
        int y = 7;

        return "(" + x + "," + y + ")";
    }
    public static String stringBufferThis() {
        int x = 5;
        int y = 7;

        StringBuffer sb = new StringBuffer("(");
        sb.append(x);
        sb.append(",");
        sb.append(y);
        sb.append(")");
        return sb.toString();
    }
}
