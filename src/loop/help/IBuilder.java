package loop.help;

public interface IBuilder<C, B extends IBuilder<C, B>> {
    B _this();

    C instance() throws Exception;

    C build() throws Exception;
}
