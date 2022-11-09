package main;

public class TestLogging implements TestLoggingInterface {

//    @Log
    @Override
    public void calculation(int param) {
//        System.out.println("TestLogging.calculation there.");
    }

    @Log
    @Override
    public void calculation(int param, int a) {
//        System.out.println("TestLogging.calculation2 there.");
    }

//    @Log
    @Override
    public void calculation(int param, String a) {
//        System.out.println("TestLogging.calculation3 there.");
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
