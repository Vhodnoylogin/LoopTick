package loop.constant.tickrate;

import loop.common.AbstractLoop;

public class ConstantTickrateLoop extends AbstractLoop {
    protected Double tickrate;

    protected ConstantTickrateLoop() {

    }

    public static ConstantTickrateLoopBuilder builder() {
        return new ConstantTickrateLoopBuilder();
    }

    @Override
    protected Double getDelta() {
        return tickrate;
    }

    @Override
    protected void startTick() {
    }

    @Override
    protected void endTick() {
    }

    public static abstract class ConstantTickrateLoopBuilderAbstract<
            C extends ConstantTickrateLoop
            , B extends ConstantTickrateLoopBuilderAbstract<C, B>
            > extends AbstractLoopBuilder<C, B> {
        protected Double tickrate = 30.0;

        public B setTickRate(Double delta) {
            this.tickrate = delta;
            return _this();
        }

        public B setTickRate(Integer delta) {
            this.tickrate = Double.valueOf(delta);
            return _this();
        }

        @Override
        public C build() throws Exception {
            C instance = super.build();
            instance.tickrate = 1000 / this.tickrate;
            return instance;
        }
    }

    public static class ConstantTickrateLoopBuilder extends
            ConstantTickrateLoopBuilderAbstract<ConstantTickrateLoop, ConstantTickrateLoopBuilder> {

        @Override
        protected ConstantTickrateLoopBuilder _this() {
            return this;
        }

        @Override
        protected ConstantTickrateLoop instance() {
            return new ConstantTickrateLoop();
        }

        @Override
        public ConstantTickrateLoop build() {
            try {
                return super.build();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
