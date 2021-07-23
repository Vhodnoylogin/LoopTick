package loop.help.consumers;

import second.loop.common.AbstractLoop;

@FunctionalInterface
public interface LoopConsumer extends BiConsumerWithException<Double, AbstractLoop.StopLoop> {
}
