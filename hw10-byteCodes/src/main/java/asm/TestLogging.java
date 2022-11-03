package asm;

public class TestLogging {

    @Log
    public void calculation(int param1) {
        System.out.println("calculation original");
    }

    public void otherMethod(int i) {
        System.out.println("otherMethod original");
    }
}
