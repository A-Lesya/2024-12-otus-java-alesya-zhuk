package ru.otus.test_examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S112", "java:S1186"})
public class ExceptionInBeforeTest {
    @Before
    public void before1() {}

    @Before
    public void before2() {
        throw new RuntimeException("some exception in before2");
    }

    @Before
    public void before3() {
        throw new RuntimeException("some exception in before3");
    }

    @Before
    public void before4() {}

    @Test
    public void test1() {
        throw new RuntimeException("this exception in test should not be reached");
    }

    @After
    public void after1() {}

    @After
    public void after2() {}
}
