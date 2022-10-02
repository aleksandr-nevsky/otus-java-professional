package cc.nevsky.otus;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Hello world with guava.
 */
public class HelloOtus {
    public static void main(String[] args) {
        System.out.println("Create List using com.google.common.collect.Lists");
        List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi");

        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }
}