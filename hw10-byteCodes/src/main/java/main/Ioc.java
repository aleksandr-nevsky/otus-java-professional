package main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface getTestLoggingCass() {
        InvocationHandler invocationHandler = new TestLoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, invocationHandler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final Set<Method> annotatedMethods = new HashSet<>();


        public TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            init();
        }

        /**
         * Инициализация состояния класса.
         */
        private void init() {
            Method[] methodSet = TestLogging.class.getMethods();
            for (Method m : methodSet) {
                if (m.isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(m);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();

            annotatedMethods.forEach(m -> {
                if (m.getName().equals(methodName) && m.getParameterCount() == args.length) {
                    System.out.printf("executed method: %s, param: %s%n", m.getName(), Arrays.toString(args));
                }
            });

            return method.invoke(testLogging, args);
        }

    }
}
