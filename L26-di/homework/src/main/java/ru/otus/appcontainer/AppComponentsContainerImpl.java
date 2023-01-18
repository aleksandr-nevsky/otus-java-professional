package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        try {
            Object classInstance = configClass.getConstructor().newInstance();
            Method[] methods = Arrays.stream(configClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(o -> o.getAnnotation(AppComponent.class).order()))
                    .toArray(Method[]::new);

            for (Method method : methods) {
                AppComponent appComponent = method.getAnnotation(AppComponent.class);
                Object invokedMethod = callMethod(classInstance, method);
                boolean isNotFound = true;
                try {
                    getAppComponent(appComponent.name());
                } catch (RuntimeException e) {
                    // It's ok there.
                    isNotFound = false;
                    appComponentsByName.put(appComponent.name(), invokedMethod);
                    appComponents.add(invokedMethod);
                }

                if (isNotFound) {
                    throw new RuntimeException(appComponent.name() + " is present");
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object callMethod(Object classInstance, Method method) {
        try {
            method.setAccessible(true);
            Parameter[] params = method.getParameters();
            Object[] paramList = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                paramList[i] = getAppComponent(params[i].getType());
            }

            return method.invoke(classInstance, paramList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> filteredList = appComponents.stream().filter(p -> componentClass.isAssignableFrom(p.getClass())).toList();
        if (filteredList.size() != 1) {
            throw new RuntimeException("Incorrect component count.");
        }

        return (C) filteredList.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object objectToReturn = appComponentsByName.get(componentName);
        if (objectToReturn == null) {
            throw new RuntimeException("Component not found.");
        }
        return (C) objectToReturn;
    }
}
