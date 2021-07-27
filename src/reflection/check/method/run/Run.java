package reflection.check.method.run;

import reflection.check.method.data.Checker;
import reflection.check.method.data.MethodContainer;

public class Run {
    public static void main(String[] args) {
        Run o1 = new Run();
        MethodContainer o2 = new MethodContainer(5);
        MethodContainer o3 = new MethodContainer(-100);

        String method = "method";
        Checker.checkNrun(o2, method);
        Checker.checkNrun(o3, method);
        Checker.checkNrun(o1, method);
    }
}
