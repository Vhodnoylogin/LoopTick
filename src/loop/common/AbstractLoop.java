package loop.common;

import loop.help.builder.Builder;
import loop.help.consumers.LoopConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractLoop implements Runnable {
    protected List<LoopConsumer> actions;
    protected StopLoop stop = new StopLoop(this);
    protected Queue<Exception> exceptionList;

    protected abstract Double getDelta();

    protected abstract void startTick();

    protected abstract void endTick();

    protected void tick(Double delta) {
        try {
            TimeUnit.MILLISECONDS.sleep(delta.longValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.exceptionList.add(e);
        }
    }

    @Override
    public void run() {
        this.loop();
    }

    protected void loop() {
        while (!this.stop.stopLoop()) {
            this.startTick();
            Double delta = this.getDelta();
            for (LoopConsumer action : this.actions) {
                try {
                    action.accept(delta, this.stop);
                } catch (Exception e) {
                    this.exceptionList.add(e);
                }
            }
            this.endTick();
            this.tick(delta);
        }
    }

    public static class StopLoop {
        protected AbstractLoop cycle;
        protected boolean stopFlag = false;

        protected StopLoop(AbstractLoop cycle) {
            this.cycle = cycle;
        }

        public void stop() {
            this.stopFlag = true;
        }

        protected boolean stopLoop() {
            return this.stopFlag;
        }
    }

    public static abstract class AbstractLoopBuilder<
            C extends AbstractLoop
            , B extends AbstractLoopBuilder<C, B>
            > extends Builder<C, B> {
        protected List<LoopConsumer> actions;

        {
            this.actions = new ArrayList<>();
        }

        public B setAction(LoopConsumer action) {
            this.actions.add(action);
            return _this();
        }


        @Override
        public C build() throws Exception {
            C instance = super.build();
            instance.actions = this.actions;
            instance.exceptionList = new ArrayBlockingQueue<>(100);
            return instance;
        }
    }
}
