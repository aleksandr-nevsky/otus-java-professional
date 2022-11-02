package cc.nevsky.otus;

/**
 * Счётчик тестов.
 */
public class Counter {
    private int success;
    private int fail;

    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    public void addSuccess() {
        success += 1;
    }

    public void addFail() {
        fail += 1;
    }
}
