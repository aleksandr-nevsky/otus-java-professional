package cc.nevsky.otus;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для запуска.
 */
public class Main {
    private static int success = 0;
    private static int fail = 0;

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        List<String> classesForTests = new ArrayList<>();
        classesForTests.add("cc.nevsky.otus.classes.SimpleClass");
        classesForTests.add("cc.nevsky.otus.classes.AnotherSimpleClass");

        for (String className : classesForTests) {
            Tests tests = new Tests();
            tests.run(className);
            success += tests.getSuccess();
            fail += tests.getFail();
        }

        System.out.printf("%s tests started. %s success, %s failed.%n\n\n", success + fail, success, fail);
        success = 0;
        fail = 0;

        for (String className : classesForTests) {
            MoreTests moreTests = new MoreTests();
            moreTests.run(className);
            success += moreTests.getSuccess();
            fail += moreTests.getFail();
        }

        System.out.printf("%s more tests started. %s success, %s failed.%n", success + fail, success, fail);
    }

}