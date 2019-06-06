package com.teee.referencestation.encrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class TestThread {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(8);
        service.execute(() -> System.out.println("call1"));
        FutureTask task = (FutureTask) service.submit(() -> {
            System.out.println("call2");
        });
        FutureTask task2 = (FutureTask) service.submit(new RunnableTest());
        service.shutdown();

        List<String> list = new ArrayList<>(8);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List list2 = list.stream().map(o -> Integer.valueOf(o) + 1).collect(Collectors.toList());
        list.forEach(o -> System.out.println(o));
        list2.forEach(o -> System.out.println(o));
    }

    static class RunnableTest implements Runnable {
        @Override
        public void run() {
            System.out.println("call3");
        }
    }
}
