package loop.help.consumers;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface BiConsumerWithException<T, U> {
    void accept(T t, U u) throws Exception;

    default BiConsumerWithException<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);

        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}