package loop;

import java.util.concurrent.atomic.AtomicInteger;

class LoopTest {
    long time;
    double percent = 0.05;

    @org.junit.jupiter.api.Test
    void run() {
//        Executor exec = Executors.newSingleThreadExecutor();
//        Thread.currentThread().
        int tickrate = 30;
        int endCount = 100;

        int codeTime = Math.toIntExact(wrapCodeWithTime(() -> {
            AtomicInteger it = new AtomicInteger();
            Loop action = Loop.builder()
                    .setTickRate((double) tickrate)
                    .setAction((delta, stop) -> {
//                    System.out.println(delta);
                        if (it.getAndIncrement() > endCount) {
                            stop.stop();
                        }
                    })
                    .build();
            action.run();
//            Future<?> result = Executors.newSingleThreadExecutor().submit(action);
//            while (!result.isDone()) {
//            }
        }) / 1000000);

        int checkTime = Math.toIntExact(wrapCodeWithTime(() -> {
            int it = 0;
            while (it++ <= endCount) {
                try {
                    Thread.sleep(tickrate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) / 1000000);

        double res = (codeTime - checkTime) * 1.0 / codeTime;
        System.out.println(codeTime + " " + checkTime + " " + String.format("%1$,.2f%%", res * 100));

        if (res < this.percent) {
            assert true;
        } else {
            assert false;
        }

//        time = System.nanoTime();
//        time = System.nanoTime() - time;
//        System.out.println("END TIME = " + (time / 1000000));
    }

//
//    @BeforeAll
//    public void before(){
//
//    }

    protected long wrapCodeWithTime(Runnable run) {
        long time = System.nanoTime();
        run.run();
        return System.nanoTime() - time;
    }
}