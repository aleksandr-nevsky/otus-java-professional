package asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DemoAsm {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ClassReader classReader = new ClassReader("asm.TestLogging");
        ClassWriter classWriter = new ClassWriter(classReader, 0);
        ClassVisitor actorVisitor = new MyClassVisitor(classWriter);
        classReader.accept(actorVisitor, 0);

        var testLoggingClass = new MyClassLoader().defineClass("asm.TestLogging", classWriter.toByteArray());

        Constructor<?> constructor = testLoggingClass.getConstructor();
        Method method = testLoggingClass.getMethod("calculation", int.class);
        method.invoke(constructor.newInstance(), 6);
    }

    public static class MyClassLoader extends ClassLoader {
        Class<?> defineClass(String className, byte[] bytecode) {
            return super.defineClass(className, bytecode, 0, bytecode.length);
        }
    }
}
