package com.hutgin2;

import org.junit.Test;

public class Pad {

    @Test
    public void test() {
        A a = new B();
        a.m();
        System.out.println(a.p);
        a(null);


    }

    public void a(Object a) {
        System.out.println("Object");
    }

    public void a(Object... a) {
        System.out.println("Object...");
    }

    public void a(Integer a) {
        System.out.println("Integer");
    }
//    public void a(Integer... a) {
//        System.out.println("Integer...");
//    }


    class A {
        public String p = "A";

        void m() {
            System.out.println("A");
        }
    }

    class B extends A {
        public String p = "B";

        void m() {
            System.out.println("B");
        }
    }
}
