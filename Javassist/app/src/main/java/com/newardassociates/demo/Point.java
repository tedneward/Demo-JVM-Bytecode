package com.newardassociates.demo;

public class Point {
    public int x;
    public int y;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append(x);
        sb.append(",");
        sb.append(y);
        sb.append(")");
        return sb.toString();
        
        //return "(" + x + "," + y + ")";
    }
}

