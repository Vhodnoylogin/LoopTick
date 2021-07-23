package loop.help.builder;

public interface IBuilder<C, B extends IBuilder<C, B>> {
    C build() throws Exception;
}
