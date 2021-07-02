package loop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class Loop implements Runnable {
    protected Integer delta = 30;
    protected StopLoop stop = new StopLoop(this);
    protected BiConsumer<Double, StopLoop> action;
    protected List<Exception> exceptionList = new ArrayList<>();

    @Override
    public void run() {
        Double delta = 1000.0 / this.delta;
        while (!this.stop.stopLoop()) {
            this.action.accept(delta / 1000, this.stop);
            try {
                TimeUnit.MILLISECONDS.sleep(delta.longValue());
            } catch (InterruptedException e) {
                this.exceptionList.add(e);
            }
        }
    }

    public Loop setAction(BiConsumer<Double, StopLoop> actions) {
        this.action = actions;
        return this;
    }

    public Loop setTickRate(Integer delta) {
        this.delta = delta;
        return this;
    }

    public static class StopLoop {
        protected Loop cycle;
        protected boolean stopFlag = false;

        protected StopLoop(Loop cycle) {
            this.cycle = cycle;
        }

        public void stop() {
            this.stopFlag = true;
        }

        protected boolean stopLoop() {
            return this.stopFlag;
        }
    }
}
