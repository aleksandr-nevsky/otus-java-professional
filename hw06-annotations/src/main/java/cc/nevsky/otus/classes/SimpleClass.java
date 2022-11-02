package cc.nevsky.otus.classes;

import cc.nevsky.otus.annotations.After;
import cc.nevsky.otus.annotations.Before;
import cc.nevsky.otus.annotations.Test;

/**
 * Простой класс для тестирования.
 */
@SuppressWarnings("unused")
public class SimpleClass {

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
        System.out.printf("TestClass afterMethod this.hashCode() = %s%n%n", this.hashCode());
    }
}
