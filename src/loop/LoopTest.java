package loop;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

class LoopTest {
    long time;

    @org.junit.jupiter.api.Test
    void run() {
        Integer tickrate = 30;
        AtomicInteger it = new AtomicInteger();
        Loop action = new Loop()
                .setTickRate(tickrate)
                .setAction((delta, stop) -> {
//                    System.out.println(delta);
                    if (it.getAndIncrement() > 100) {
                        stop.stop();
                    }
                });
        time = System.nanoTime();
        Future<?> result = Executors.newSingleThreadExecutor().submit(action);
        while (!result.isDone()) {
        }
        time = System.nanoTime() - time;
        System.out.println("END TIME = " + (time / 1000000));
    }
//
//    @BeforeAll
//    public void before(){
//
//    }
}