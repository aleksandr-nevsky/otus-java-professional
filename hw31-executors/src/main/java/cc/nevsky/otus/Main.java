package cc.nevsky.otus;


public class Main {
    private int currentThreadNumber = 1;

    private static final int TOTAL_THREAD_COUNT = 2;
    private static final int COUNT_TO = 10;

    private synchronized void action(int priority) {
        int currentNumber = 0;
        int higherNumber = 0;

        while (!Thread.currentThread().isInterrupted()) {
            try {

                while (priority != currentThreadNumber) {
                    this.wait();
                }
                if (higherNumber < COUNT_TO) {
                    currentNumber++;
                    higherNumber++;
                } else {
                    currentNumber--;
                }

                System.out.println(Thread.currentThread().getName() + " " + currentNumber);
                this.notifyAll();

                if (currentNumber <= 1 && higherNumber >= COUNT_TO) {
                    Thread.currentThread().interrupt();
                }

                currentThreadNumber = priority == TOTAL_THREAD_COUNT ? 1 : ++currentThreadNumber;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        new Thread(() -> main.action(1)).start();
        new Thread(() -> main.action(2)).start();
    }
}