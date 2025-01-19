package ru.otus;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

@SuppressWarnings({"java:S106"})
public class HelloOtus {
    public static void main(String[] args) {
        ImmutableCollection<String> collection = ImmutableSet.of("one", "two", "three");

        System.out.println(collection);
    }
}
