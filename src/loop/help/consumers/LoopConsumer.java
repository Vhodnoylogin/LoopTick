package loop.help.consumers;

import loop.common.AbstractLoop;

@FunctionalInterface
public interface LoopConsumer extends BiConsumerWithException<Double, AbstractLoop.StopLoop> {
}
