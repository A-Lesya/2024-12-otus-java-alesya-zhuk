package ru.otus.test_examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S112", "java:S1186", "java:S106"})
public class SimpleTest {
    @Before
    public void before() {
        System.out.println("before");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2 (exception here)");
        throw new RuntimeException("some exception in test");
    }

    @Test
    public void test3() {
        System.out.println("test3 (error here)");
        throw new AssertionError("some error in test");
    }

    @Test
    public void test4() {
        System.out.println("test4");
    }

    @After
    public void after1() {
        System.out.println("after1");
    }

    @After
    public void after2() {
        System.out.println("after2");
    }
}
