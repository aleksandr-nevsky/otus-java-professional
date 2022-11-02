package cc.nevsky.otus;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для запуска.
 */
public class Main {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Counter counter = new Counter();
        List<String> classesForTests = new ArrayList<>();
        classesForTests.add("cc.nevsky.otus.classes.SimpleClass");
        classesForTests.add("cc.nevsky.otus.classes.AnotherSimpleClass");

        for (String className : classesForTests) {
            Tests tests = new Tests();

            tests.run(className, counter);
        }

        System.out.printf("%s tests started. %s success, %s failed.%n\n\n", counter.getSuccess() + counter.getFail(), counter.getSuccess(), counter.getFail());
    }
}