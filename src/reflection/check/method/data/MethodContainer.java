package reflection.check.method.data;

public class MethodContainer {
    protected int x = 0;

    public MethodContainer(int x) {
        this.x = x;
    }

    public int method(int n) {
        return n + x + 1;
    }
}
