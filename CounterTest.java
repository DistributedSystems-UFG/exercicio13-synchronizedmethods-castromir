class Counter {

    private int c = 0;

    // Versão sincronizada
    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}

public class CounterTest {

    static class IncrementTask implements Runnable {

        private final Counter counter;

        public IncrementTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {

            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        }
    }

    static class DecrementTask implements Runnable {

        private final Counter counter;

        public DecrementTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {

            for (int i = 0; i < 100000; i++) {
                counter.decrement();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        // Duas ou mais threads concorrentes
        Thread t1 = new Thread(new IncrementTask(counter));
        Thread t2 = new Thread(new IncrementTask(counter));
        Thread t3 = new Thread(new DecrementTask(counter));
        Thread t4 = new Thread(new DecrementTask(counter));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Valor final do contador: " + counter.value());
    }
}