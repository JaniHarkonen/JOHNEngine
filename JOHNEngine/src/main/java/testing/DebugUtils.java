package testing;

public class DebugUtils {

    public static void log(Object me, Object... message) {
        System.out.println(me + ":");

        for (Object m : message)
            System.out.println(m);
    }
}
