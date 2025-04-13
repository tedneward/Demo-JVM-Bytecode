package com.newardassociates.demo;

public class Outer {
    public class Inner {
        private Outer outer;
    }
    public Inner newInner() {
        return new Inner();
    }

    public static class StaticInner {
        private Outer outer;
    }
    public StaticInner newStaticInner() {
        return new StaticInner();
    }

    public static void main(String... args) {
        Outer o = new Outer();

        Outer.Inner oi = o.newInner();
        Outer.Inner oi2 = o.new Inner();

        Outer.StaticInner osi = o.newStaticInner();
        Outer.StaticInner osi2 = new Outer.StaticInner();
    }
}
