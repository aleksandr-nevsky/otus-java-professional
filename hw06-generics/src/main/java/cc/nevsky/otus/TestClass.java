package cc.nevsky.otus;

import cc.nevsky.otus.annotations.After;
import cc.nevsky.otus.annotations.Before;
import cc.nevsky.otus.annotations.Test;

public class TestClass {

    @Before
    public void beforeMethod() {
        System.out.println("TestClass beforeMethod this.hashCode() = " + this.hashCode());
    }


    @Test
    public void testMethod1() {
        System.out.println("TestClass testMethod1 this.hashCode() = " + this.hashCode());
    }

    @Test
    public void testMethod2() {
        System.out.println("TestClass testMethod2 this.hashCode() = " + this.hashCode());
    }


    @After
    public void afterMethod() {
        System.out.println("TestClass afterMethod this.hashCode() = " + this.hashCode());
    }
}
