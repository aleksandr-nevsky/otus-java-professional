package cc.nevsky.otus;

import cc.nevsky.otus.annotations.After;
import cc.nevsky.otus.annotations.Before;
import cc.nevsky.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int success = 0;
    private static int fail = 0;

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        run("cc.nevsky.otus.TestClass");
        run("cc.nevsky.otus.AnotherTestClass");
        System.out.printf("%s tests started. %s success, %s failed.%n", success + fail, success, fail);
    }

    private static void run(String className) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();

        List<Method> methodsAnnotatedBefore = getAnnotationMethods(clazz, Before.class);
        List<Method> methodsAnnotatedTest = getAnnotationMethods(clazz, Test.class);
        List<Method> methodsAnnotatedAfter = getAnnotationMethods(clazz, After.class);

        if (methodsAnnotatedBefore.size() > 1 || methodsAnnotatedAfter.size() > 1) {
            throw new RuntimeException("To many Before or After annotation.");
        }

        try {
            runBefore(methodsAnnotatedBefore, instance);
            runTests(methodsAnnotatedTest, instance);
        } finally {
            runAfter(methodsAnnotatedAfter, instance);
        }
    }

    private static void runBefore(List<Method> methodsAnnotatedBefore, Object instance) throws InvocationTargetException, IllegalAccessException {
        if (methodsAnnotatedBefore.size() == 1) {
            methodsAnnotatedBefore.get(0).invoke(instance);
        }
    }

    private static void runTests(List<Method> methodsAnnotatedTest, Object instance) {
        methodsAnnotatedTest.forEach(m -> {
            try {
                m.invoke(instance);
                success += 1;
            } catch (Exception e) {
                fail += 1;
                System.out.println(e.getCause() + " in " + m);
            }
        });
    }

    private static void runAfter(List<Method> methodsAnnotatedAfter, Object instance) throws InvocationTargetException, IllegalAccessException {
        if (methodsAnnotatedAfter.size() == 1) {
            methodsAnnotatedAfter.get(0).invoke(instance);
        }
    }

    private static List<Method> getAnnotationMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getMethods();
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method);
            }
        }

        return annotatedMethods;
    }
}