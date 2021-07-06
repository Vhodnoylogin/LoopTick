package loop;

import loop.help.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class Loop implements Runnable {
    protected Double tickrate;
    protected BiConsumer<Double, StopLoop> action;
    protected StopLoop stop = new StopLoop(this);
    protected List<Exception> exceptionList = new ArrayList<>();

    public static LoopBuilder builder() {
        return new LoopBuilder();
    }

    protected void tick(Double delta) {
        try {
            System.out.println(delta.longValue());
            TimeUnit.MILLISECONDS.sleep(delta.longValue());
        } catch (InterruptedException e) {
            this.exceptionList.add(e);
        }
    }

    @Override
    public void run() {
//        double delta = 1000.0 / this.delta;
        while (!this.stop.stopLoop()) {
            this.action.accept(tickrate, this.stop);
            tick(tickrate);
        }
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

    public static abstract class LoopBuilderAbstract<C extends Loop, B extends LoopBuilderAbstract<C, B>> extends Builder<C, B> {
        protected Double tickrate = 30.0;
        protected BiConsumer<Double, StopLoop> action;

        public B setAction(BiConsumer<Double, StopLoop> actions) {
            this.action = actions;
            return _this();
        }

        public B setTickRate(Double delta) {
            this.tickrate = delta;
            return _this();
        }

        public B setTickRate(Integer delta) {
            this.tickrate = Double.valueOf(delta);
            return _this();
        }

        @Override
        public C build() {
            try {
                C instance = super.build();
                instance.action = action;
                instance.tickrate = tickrate;
                return instance;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static class LoopBuilder extends LoopBuilderAbstract<Loop, LoopBuilder> {
        @Override
        public LoopBuilder _this() {
            return this;
        }

        @Override
        public Loop instance() {
            return new Loop();
        }
    }
}
