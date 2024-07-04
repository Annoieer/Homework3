package homework_3.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomThreadPoolExecutor {
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private final ArrayList<CustomThread> customThreads = new ArrayList<>();
    private final Object monitor = new Object();
    private final int threadCount;
    private boolean isShutdown;

    public CustomThreadPoolExecutor(int threadCount) {
        this.threadCount = threadCount;
        this.isShutdown = false;
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < threadCount; i++) {
            CustomThread customThread = new CustomThread(taskQueue, monitor);
            customThreads.add(customThread);
            customThread.start();
        }
    }

    public void execute(Runnable task) {
        synchronized (monitor) {
            if (isShutdown) {
                throw new IllegalStateException("Пул потоков остановлен");
            }
            taskQueue.addLast(task);
            monitor.notifyAll();
        }
    }

    public void shutdown() {
        synchronized (monitor) {
            isShutdown = true;
            System.out.println("Отключен приём новых задач");
        }
    }

    public void awaitTermination() {
        while (true) {
            List<CustomThread> waitingCustomThreads = customThreads.stream()
                    .filter(thread -> Objects.equals(thread.getState(), Thread.State.WAITING))
                    .collect(Collectors.toList());

            if (waitingCustomThreads.isEmpty()) {
                shutdown();
                return;
            }
        }
    }
}
