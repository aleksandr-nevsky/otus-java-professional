package main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
        private final Method[] methodSet = TestLogging.class.getMethods();

        public TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Arrays.stream(methodSet).forEach(m -> {
                if (m.isAnnotationPresent(Log.class) && m.getName().equals(method.getName())) {
                    System.out.printf("executed method: %s, param: %s%n", m.getName(), Arrays.toString(args));
                }
            });

            return method.invoke(testLogging, args);
        }

    }
}
