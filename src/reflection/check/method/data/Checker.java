package reflection.check.method.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Checker {
    public static void checkNrun(Object obj, String methodName) {
        Method m = null;
        try {
            m = obj.getClass().getMethod(methodName, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (m != null) {
            int x = 5;
            try {
                Object res = m.invoke(obj, x);
                System.out.println(res);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("there are no such method = " + methodName);
        }
    }
}
