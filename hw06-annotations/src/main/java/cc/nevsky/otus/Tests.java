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

/**
 * Класс тестирования.
 */
public class Tests {

    /**
     * Запуск тестов.
     *
     * @param className полное имя класса.
     */
    public void run(String className, Counter counter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();

        List<Method> methodsAnnotatedBefore = getAnnotationMethods(clazz, Before.class);
        List<Method> methodsAnnotatedTest = getAnnotationMethods(clazz, Test.class);
        List<Method> methodsAnnotatedAfter = getAnnotationMethods(clazz, After.class);

        if (methodsAnnotatedBefore.size() > 1 || methodsAnnotatedAfter.size() > 1) {
            throw new RuntimeException("To many Before or After annotation.");
        }

        for (Method method : methodsAnnotatedTest) {
            Object instance = constructor.newInstance();
            try {
                runBefore(methodsAnnotatedBefore, instance);

                method.invoke(instance);
                counter.addSuccess();
            } catch (Exception e) {
                counter.addFail();
                System.out.println(e.getCause() + " in " + method);
            } finally {
                runAfter(methodsAnnotatedAfter, instance);
            }
        }
    }

    private void runBefore(List<Method> methodsAnnotatedBefore, Object instance) throws InvocationTargetException, IllegalAccessException {
        if (methodsAnnotatedBefore.size() == 1) {
            methodsAnnotatedBefore.get(0).invoke(instance);
        }
    }

    private void runAfter(List<Method> methodsAnnotatedAfter, Object instance) throws InvocationTargetException, IllegalAccessException {
        if (methodsAnnotatedAfter.size() == 1) {
            methodsAnnotatedAfter.get(0).invoke(instance);
        }
    }

    private List<Method> getAnnotationMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
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
