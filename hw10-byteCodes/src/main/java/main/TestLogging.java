package main;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {
//        System.out.println("TestLogging.calculation there.");
    }

    @Override
    public void otherMethod(int i) {
//        System.out.println("TestLogging.otherMethod there.");
    }

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
