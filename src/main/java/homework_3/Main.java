package homework_3;

import homework_3.threads.CustomThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
        int threadCount = 4;
        int tasks = threadCount * 5;

        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(threadCount);
        for (int i = 0; i < tasks; i++) {
            int taskId = i;
            customThreadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "   Start::  Task " + taskId);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "   End:: Task " + taskId);
            });
        }

        customThreadPoolExecutor.awaitTermination();

        try {
            customThreadPoolExecutor.execute(() -> {
                System.out.println("Пытаемся добавить новую задачу");
                System.out.println(Thread.currentThread().getName() + "   Start::  AnotherTask");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "   End:: AnotherTask");
            });
        } catch (IllegalStateException exception) {
            System.out.println("Добавление новой задачи отклонено: " + exception.getMessage());
        }

    }
}
