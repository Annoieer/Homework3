package homework_3.threads;

import java.util.LinkedList;

public class CustomThread extends Thread {
    private final LinkedList<Runnable> taskQueue;
    private final Object monitor;

    public CustomThread(LinkedList<Runnable> taskQueue, Object monitor) {
        this.monitor = monitor;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            while (taskQueue.isEmpty()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while (!taskQueue.isEmpty()) {
            taskQueue.removeFirst().run();
        }
        Thread.currentThread().interrupt();
    }
}
