package main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

        /**
         * Проверка параметров методов на одинаковость.
         *
         * @return Если параметры методов одинаковые - true. Иначе false.
         */
        private boolean isMethodsParametersMatch(Method method1, Method method2) {
            if (method1.getParameterCount() != method2.getParameterCount()) {
                return false;
            }

            List<? extends Class<?>> classList1 = Arrays.stream(method1.getParameters()).map(Parameter::getType).toList();
            List<? extends Class<?>> classList2 = Arrays.stream(method2.getParameters()).map(Parameter::getType).toList();

            return classList1.equals(classList2);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();

            annotatedMethods.forEach(m -> {
                if (m.getName().equals(methodName) && isMethodsParametersMatch(method, m)) {
                    System.out.printf("executed method: %s, param: %s%n", m.getName(), Arrays.toString(args));
                }
            });

            return method.invoke(testLogging, args);
        }
    }
}
