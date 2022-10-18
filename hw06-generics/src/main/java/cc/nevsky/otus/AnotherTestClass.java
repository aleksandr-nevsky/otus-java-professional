package cc.nevsky.otus;

import cc.nevsky.otus.annotations.After;
import cc.nevsky.otus.annotations.Before;
import cc.nevsky.otus.annotations.Test;

public class AnotherTestClass {

    @Before
    public void beforeMethod() {
        System.out.println("AnotherTestClass beforeMethod this.hashCode() = " + this.hashCode());
    }

    @Test
    public void testMethod1() {
        System.out.println("AnotherTestClass testMethod1 this.hashCode() = " + this.hashCode());
    }

    @Test
    public void testMethod2() {
        System.out.println("AnotherTestClass testMethod2 this.hashCode() = " + this.hashCode());
        throw new RuntimeException("Something wrong");
    }

    @Test
    public void testMethod3() {
        System.out.println("AnotherTestClass testMethod3 this.hashCode() = " + this.hashCode());
    }

    @After
    public void afterMethod() {
        System.out.println("AnotherTestClass afterMethod this.hashCode() = " + this.hashCode());
    }
}
