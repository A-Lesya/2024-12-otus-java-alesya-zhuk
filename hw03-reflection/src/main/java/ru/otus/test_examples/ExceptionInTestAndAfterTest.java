package ru.otus.test_examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S112", "java:S1186"})
public class ExceptionInTestAndAfterTest {
    @Before
    public void before1() {}

    @Before
    public void before2() {}

    @Test
    public void test1() {
        throw new AssertionError("some error in test");
    }

    @Test
    public void test2() {}

    @After
    public void after1() {}

    @After
    public void after2() {
        throw new RuntimeException("some exception in after method");
    }

    @After
    public void after3() {}
}
