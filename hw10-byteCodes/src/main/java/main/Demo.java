package main;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.getTestLoggingCass();
        testLogging.calculation(6);
        testLogging.calculation(6, 3);
        testLogging.otherMethod(2);
    }
}
