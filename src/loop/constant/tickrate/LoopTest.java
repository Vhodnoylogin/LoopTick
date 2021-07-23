package loop.constant.tickrate;

import loop.help.consumers.LoopConsumer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class LoopTest {
    double percent = 0.05;

    @org.junit.jupiter.api.Test
    void run() {
        int tickrate = 30;
        int endCount = 100;

        AtomicInteger it1 = new AtomicInteger();
        LoopConsumer action = (delta, stop) -> {
//                    System.out.println(delta);
            if (it1.getAndIncrement() > endCount) {
                stop.stop();
            }
        };

        ConstantTickrateLoop run1 = ConstantTickrateLoop.builder()
                .setTickRate(tickrate)
                .setAction(action)
                .build();

        AtomicInteger it2 = new AtomicInteger();
        long revTickrate = 1000 / tickrate;
        Runnable run2 = () -> {
            while (it2.getAndIncrement() <= endCount) {
                try {
                    TimeUnit.MILLISECONDS.sleep(revTickrate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        int codeTime = Math.toIntExact(wrapCodeWithTime(run1));
        int checkTime = Math.toIntExact(wrapCodeWithTime(run2));

        double res = (codeTime - checkTime) * 1.0 / codeTime;
        System.out.println(codeTime + " " + checkTime + " " + String.format("%1$,.2f%%", res * 100));

        if (res < this.percent) {
            assert true;
        } else {
            assert false;
        }

    }

//
//    @BeforeAll
//    public void before(){
//
//    }

    protected long wrapCodeWithTime(Runnable run) {
        long time = System.nanoTime();
        run.run();
        return (System.nanoTime() - time) / 1000000;
    }
}
