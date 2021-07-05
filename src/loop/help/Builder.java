package loop.help;

public abstract class Builder<C, B extends Builder<C, B>> implements IBuilder<C, B> {
    @Override
    public C build() throws Exception {
        return instance();
    }
}
